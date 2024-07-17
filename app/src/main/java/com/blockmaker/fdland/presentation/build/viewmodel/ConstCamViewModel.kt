//package com.blockmaker.fdland.presentation.build.viewmodel
//
//import android.content.ContentValues
//import android.content.Context
//import android.content.Intent
//import android.net.Uri
//import android.provider.MediaStore
//import android.util.Log
//import android.widget.Toast
//import androidx.camera.core.CameraSelector
//import androidx.camera.core.ImageCapture
//import androidx.camera.core.ImageCaptureException
//import androidx.camera.core.Preview
//import androidx.camera.lifecycle.ProcessCameraProvider
//import androidx.camera.view.PreviewView
//import androidx.core.content.ContextCompat
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.blockmaker.fdland.data.repository.ConstRepository
//import com.blockmaker.fdland.presentation.build.view.ConstCamActivity
//import com.blockmaker.fdland.presentation.build.view.ConstResultActivity
//import kotlinx.coroutines.launch
//import okhttp3.MediaType.Companion.toMediaTypeOrNull
//import okhttp3.MultipartBody
//import okhttp3.RequestBody.Companion.asRequestBody
//import java.io.File
//import java.text.SimpleDateFormat
//import java.util.Locale
//import java.util.concurrent.ExecutorService
//import java.util.concurrent.Executors
//
//class ConstCamViewModel(private val constRepository: ConstRepository) : ViewModel() {
//
//    private lateinit var cameraExecutor: ExecutorService
//    private var imageCapture: ImageCapture? = null
//
//    fun initialize() {
//        cameraExecutor = Executors.newSingleThreadExecutor()
//    }
//
//    fun shutdown() {
//        cameraExecutor.shutdown()
//    }
//
//    fun startCamera(context: Context, previewView: PreviewView) {
//        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
//
//        cameraProviderFuture.addListener({
//            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
//            val preview = Preview.Builder().build().also {
//                it.setSurfaceProvider(previewView.surfaceProvider)
//            }
//
//            imageCapture = ImageCapture.Builder().build()
//
//            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
//
//            try {
//                cameraProvider.unbindAll()
//                cameraProvider.bindToLifecycle(context as ConstCamActivity, cameraSelector, preview, imageCapture)
//            } catch (exc: Exception) {
//                Log.e(TAG, "Use case 바인딩 실패", exc)
//            }
//
//        }, ContextCompat.getMainExecutor(context))
//    }
//
//    fun takePhoto(context: Context) {
//        val imageCapture = imageCapture ?: return
//
//        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis())
//        val contentValues = ContentValues().apply {
//            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
//            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
//        }
//
//        val outputOptions = ImageCapture.OutputFileOptions.Builder(
//            context.contentResolver,
//            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//            contentValues
//        ).build()
//
//        imageCapture.takePicture(
//            outputOptions,
//            ContextCompat.getMainExecutor(context),
//            object : ImageCapture.OnImageSavedCallback {
//                override fun onError(exc: ImageCaptureException) {
//                    Log.e(TAG, "사진 찍기 실패: ${exc.message}", exc)
//                }
//
//                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
//                    val msg = "사진 찍기 성공: ${output.savedUri}"
//                    showToast(context, msg)
//                    Log.d(TAG, msg)
//
//                    output.savedUri?.let { uri ->
//                        val file = File(getRealPathFromURI(context, uri)!!)
//                        uploadImage(uri, file, context)
//                    }
//                }
//            }
//        )
//    }
//
//    private fun uploadImage(uri: Uri, file: File, context: Context) {
//        viewModelScope.launch {
//            val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
//            val body = MultipartBody.Part.createFormData("composition_image", file.name, requestFile)
//
//            val response = constRepository.setConstImg(body)
//            if (response.isSuccessful) {
//                Log.d(TAG, "이미지 전송 성공")
//                moveToLoadingView(context, uri.toString())
//            } else {
//                Log.e(TAG, "이미지 전송 실패: ${response.message()}")
//                showToast(context, "이미지 전송 실패")
//            }
//        }
//    }
//
//    private fun moveToLoadingView(context: Context, imageUrl: String) {
//        val intent = Intent(context, ConstResultActivity::class.java)
//        intent.putExtra("image_url", imageUrl)
//        context.startActivity(intent)
//    }
//
//    private fun getRealPathFromURI(context: Context, uri: Uri): String? {
//        var path: String? = null
//        val projection = arrayOf(MediaStore.Images.Media.DATA)
//        val cursor = context.contentResolver.query(uri, projection, null, null, null)
//        cursor?.let {
//            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
//            it.moveToFirst()
//            path = it.getString(columnIndex)
//            it.close()
//        }
//        return path
//    }
//
//    private fun showToast(context: Context, message: String) {
//        (context as ConstCamActivity).runOnUiThread {
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    companion object {
//        private const val TAG = "CameraXApp"
//        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
//    }
//}
package com.blockmaker.fdland.presentation.build.viewmodel

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blockmaker.fdland.data.repository.ConstRepository
import com.blockmaker.fdland.presentation.build.view.ConstCamActivity
import com.blockmaker.fdland.presentation.build.view.ConstLoadingView
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ConstCamViewModel(private val repository: ConstRepository) : ViewModel() {

    private lateinit var cameraExecutor: ExecutorService
    private var imageCapture: ImageCapture? = null
    private val _selectedImage = MutableLiveData<Uri?>()

    private val _navigateToNextPage = MutableLiveData<Boolean>()
    val navigateToNextPage: LiveData<Boolean> get() = _navigateToNextPage
    private val _setConstImgIsSuccess = MutableLiveData<Boolean?>()
    val setConstImgIsSuccess: LiveData<Boolean?> get() = _setConstImgIsSuccess

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
                    showToast(context, msg)
                    Log.d(TAG, msg)

                    // 서버로 이미지 전송
                    output.savedUri?.let { uri ->
                        prepareAndSendImage(uri, context)
                    }
                }
            }
        )
    }



    private fun prepareAndSendImage(uri: Uri, context: Context) {
        viewModelScope.launch {
            try {
                val filePath = getRealPathFromURI(context, uri) ?: throw IllegalArgumentException("파일 경로를 가져오는 데 실패했습니다.")
                val file = File(filePath)
                val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val body = MultipartBody.Part.createFormData("composition_image", file.name, requestFile)

                val response = repository.setConstImg(body)
                if (response.isSuccessful) {
                    val responseBody = response.body()?.string()
                    Log.d("ConstGalleryViewModel", "Image upload successful: $responseBody")

                    // 서버에서 반환된 URL 추출
                    val imageUrl = extractImageUrl(responseBody)
                    if (imageUrl.isNotEmpty()) {
                        moveToLoadingView(context, imageUrl)
                        _setConstImgIsSuccess.value = true
                    } else {
                        Log.e("ConstGalleryViewModel", "이미지 URL이 비어있음: $responseBody")
                        _setConstImgIsSuccess.value = false
                    }
                } else {
                    Log.e("ConstGalleryViewModel", "이미지 업로드 실패: ${response.errorBody()?.string()}")
                    _setConstImgIsSuccess.value = false
                }
            } catch (e: Exception) {
                Log.e("ConstGalleryViewModel", "이미지 업로드 예외: ${e.message}", e)
                _setConstImgIsSuccess.value = false
            }
        }
    }

    private fun getRealPathFromURI(context: Context, uri: Uri): String? {
        val contentResolver: ContentResolver = context.contentResolver
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? = contentResolver.query(uri, projection, null, null, null)

        return cursor?.use {
            if (it.moveToFirst()) {
                val idx: Int = it.getColumnIndex(MediaStore.Images.Media.DATA)
                if (idx != -1) {
                    it.getString(idx)
                } else {
                    // 열 인덱스를 찾을 수 없는 경우, 다른 접근 방식 사용
                    getFileFromUri(context, uri)
                }
            } else {
                null
            }
        } ?: getFileFromUri(context, uri)
    }

    // 보조 함수: URI로부터 파일 경로를 가져옴
    private fun getFileFromUri(context: Context, uri: Uri): String? {
        return try {
            val file = File(uri.path)
            if (file.exists()) file.absolutePath else null
        } catch (e: Exception) {
            null
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

    private fun moveToLoadingView(context: Context, imageUrl: String) {
        val intent = Intent(context, ConstLoadingView::class.java).apply {
            putExtra("image_url", imageUrl)
        }
        context.startActivity(intent)
    }

    private fun showToast(context: Context, message: String) {
        (context as ConstCamActivity).runOnUiThread {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val TAG = "CameraXApp"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    }
}

