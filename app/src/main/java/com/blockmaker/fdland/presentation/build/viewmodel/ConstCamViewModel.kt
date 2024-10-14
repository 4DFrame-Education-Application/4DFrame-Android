package com.blockmaker.fdland.presentation.build.viewmodel

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blockmaker.fdland.data.repository.ConstRepository
import com.blockmaker.fdland.data.repository.PathRepository
import com.blockmaker.fdland.presentation.build.view.ConstCamActivity
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ConstCamViewModel(private val constRepository: ConstRepository) : ViewModel() {

    private lateinit var cameraExecutor: ExecutorService
    private var imageCapture: ImageCapture? = null
    private val _setConstImgIsSuccess = MutableLiveData<UploadResult?>()
    val setConstImgIsSuccess: LiveData<UploadResult?> get() = _setConstImgIsSuccess

    private val _selectedImage = MutableLiveData<Uri>()
    val selectedImage: LiveData<Uri> get() = _selectedImage

    private val _navigateToNextPage = MutableLiveData<Boolean>()
    val navigateToNextPage: LiveData<Boolean> get() = _navigateToNextPage

    fun initialize() {
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    fun shutdown() {
        cameraExecutor.shutdown()
    }

    fun startCamera(context: Context, previewView: PreviewView) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

            imageCapture = ImageCapture.Builder().build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(context as ConstCamActivity, cameraSelector, preview, imageCapture)
            } catch (exc: Exception) {
                Log.e(TAG, "Use case 바인딩 실패", exc)
            }

        }, ContextCompat.getMainExecutor(context))
    }

    fun takePhoto(context: Context) {
        val imageCapture = imageCapture ?: return

        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        }

        val outputOptions = ImageCapture.OutputFileOptions.Builder(
            context.contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        ).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "사진 찍기 실패: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val msg = "사진 찍기 성공: ${output.savedUri}"
                    Log.d(TAG, msg)

                    Toast.makeText(context, "사진을 서버로 전송 중... 서버로 보내고 있으니 잠시만 기다려주세요! ", Toast.LENGTH_LONG).show()
                    Log.d(TAG, "충분한 사진이 촬영되었습니다. 업로드를 시작합니다.")


                    // 서버로 이미지 전송
                    output.savedUri?.let { uri ->
                        prepareAndSendImage(uri, context)
                    }
                }
            }
        )
    }

    fun selectImage(uri: Uri, context: Context) {
        _selectedImage.value = uri
        _navigateToNextPage.value = true
        prepareAndSendImage(uri, context)
    }

    fun resetNavigation() {
        _navigateToNextPage.value = false
    }

    private fun prepareAndSendImage(uri: Uri, context: Context) {
        viewModelScope.launch {
            try {
                val filePath = PathRepository.getRealPathFromURI(context, uri) ?: throw IllegalArgumentException("파일 경로를 가져오는 데 실패했습니다.")
                val file = File(filePath)
                val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val body = MultipartBody.Part.createFormData("composition_image", file.name, requestFile)

                val response = constRepository.setConstImg(body)
                if (response.isSuccessful) {
                    val responseBody = response.body()?.string()
                    Log.d(TAG, "Image upload successful: $responseBody")

                    // 서버에서 반환된 URL 추출
                    val imageUrl = extractImageUrl(responseBody)
                    if (imageUrl.isNotEmpty()) {
                        _setConstImgIsSuccess.value = UploadResult(true, imageUrl)
                        _navigateToNextPage.value = true // 이미지 업로드가 성공하면 다음 페이지로 이동하도록 설정
                    } else {
                        Log.e(TAG, "이미지 URL이 비어있음: $responseBody")
                        _setConstImgIsSuccess.value = UploadResult(false, null)
                    }
                } else {
                    Log.e(TAG, "이미지 업로드 실패: ${response.errorBody()?.string()}")
                    _setConstImgIsSuccess.value = UploadResult(false, null)
                }
            } catch (e: Exception) {
                Log.e(TAG, "이미지 업로드 예외: ${e.message}", e)
                _setConstImgIsSuccess.value = UploadResult(false, null)
            }
        }
    }

    private fun extractImageUrl(responseBody: String?): String {
        return try {
            val jsonObject = JSONObject(responseBody ?: "{}")
            jsonObject.optString("image_url", "")
        } catch (e: Exception) {
            Log.e(TAG, "JSON 파싱 오류: ${e.message}")
            ""
        }
    }

    data class UploadResult(val isSuccess: Boolean, val imageUrl: String?)

    companion object {
        private const val TAG = "CameraXApp"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    }
}