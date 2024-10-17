package com.blockmaker.fdland.presentation.build.viewmodel

import android.content.ContentValues
import android.content.Context
import android.content.Intent
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
import com.blockmaker.fdland.data.repository.BuildRepository
import com.blockmaker.fdland.data.repository.PhotoRepository
import com.blockmaker.fdland.presentation.build.view.BuildCameraActivity
import com.blockmaker.fdland.presentation.build.view.BuildLoadingView
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class BuildCameraViewModel : ViewModel() {

    private lateinit var cameraExecutor: ExecutorService
    private var imageCapture: ImageCapture? = null
    private val buildRepository = BuildRepository()

    private val _navigateToNextPage = MutableLiveData<Boolean>()
    val navigateToNextPage: LiveData<Boolean> get() = _navigateToNextPage

    private val _photoCount = MutableLiveData(0)
    val photoCount: LiveData<Int> get() = _photoCount

    // 초기화 함수
    fun initialize() {
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    // 종료 함수
    fun shutdown() {
        cameraExecutor.shutdown()
    }

    // 카메라 시작 함수
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
                cameraProvider.bindToLifecycle(context as BuildCameraActivity, cameraSelector, preview, imageCapture)
            } catch (exc: Exception) {
                Log.e(TAG, "카메라 바인딩 실패", exc)
            }
        }, ContextCompat.getMainExecutor(context))
    }

    // 사진 촬영 함수
    fun takePhoto(context: Context, token: String) {  // 토큰 추가
        if (PhotoRepository.photoUris.size >= MAX_PHOTOS) {
            Toast.makeText(context, "이미 최대 사진 개수를 촬영했습니다.", Toast.LENGTH_SHORT).show()
            return
        }

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
                    Log.e(TAG, "사진 촬영 실패: ${exc.message}", exc)
                }
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    output.savedUri?.let {
                        PhotoRepository.photoUris.add(it)
                    }
                    _photoCount.value = _photoCount.value?.plus(1)
                    if (PhotoRepository.photoUris.size >= MAX_PHOTOS) {
                        Toast.makeText(context, "사진을 서버로 전송 중...", Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "충분한 사진이 촬영되었습니다. 업로드를 시작합니다.")
                        _navigateToNextPage.value = true
                        sendPhotosToServer(context, PhotoRepository.photoUris, token)  // 토큰 전달
                    }
                }
            }
        )
    }

    // 서버로 사진 전송 함수
    private fun sendPhotosToServer(context: Context, photoUris: List<Uri>, token: String) {  // 토큰 추가
        buildRepository.uploadImages(
            context,
            token,  // 토큰을 함께 전송
            photoUris,
            onSuccess = { responseBody ->
                val jsonObject = JSONObject(responseBody)
                val imageUrls = listOf(
                    jsonObject.optString("front_image"),
                    jsonObject.optString("back_image"),
                    jsonObject.optString("left_image"),
                    jsonObject.optString("right_image"),
                    jsonObject.optString("up_image")
                ).filter { it.isNotEmpty() }
                val intent = Intent(context, BuildLoadingView::class.java).apply {
                    putStringArrayListExtra("imageUrls", ArrayList(imageUrls))
                    putExtra("json_response", responseBody)
                }
                context.startActivity(intent)
            },
            onFailure = { throwable ->
                Toast.makeText(context, "사진 업로드 오류: ${throwable.message}", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "사진 업로드 오류: ${throwable.message}")
            }
        )
    }

    // 네비게이션 상태 초기화 함수
    fun resetNavigation() {
        _navigateToNextPage.value = false
    }

    companion object {
        private const val TAG = "CameraXApp"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val MAX_PHOTOS = 5
    }
}