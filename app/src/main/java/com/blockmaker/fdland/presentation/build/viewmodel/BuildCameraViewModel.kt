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

//    private lateinit var cameraExecutor: ExecutorService
//    private var imageCapture: ImageCapture? = null
//    private var photoCount = 0
//    private val maxPhotos = 5
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
//                cameraProvider.bindToLifecycle(context as BuildCameraActivity, cameraSelector, preview, imageCapture)
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
//                    Log.e(TAG, "사진 캡처 실패: ${exc.message}", exc)
//                }
//
//                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
//                    val msg = "사진 캡처 성공!"
//                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
//                    Log.d(TAG, msg)
//
//                    photoCount++
//                    if (photoCount >= maxPhotos) {
//                        // 모든 사진을 찍은 후 로딩 창으로 이동
//                        val intent = Intent(context, BuildLoadingView::class.java)
//                        context.startActivity(intent)
//                    }
//                }
//            }
//        )
//    }
//
//    companion object {
//        private const val TAG = "CameraXApp"
//        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
//    }
//}
private lateinit var cameraExecutor: ExecutorService
    private var imageCapture: ImageCapture? = null
    private val buildRepository = BuildRepository()

    private val _photoCount = MutableLiveData(0)
    val photoCount: LiveData<Int> get() = _photoCount

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
                cameraProvider.bindToLifecycle(context as BuildCameraActivity, cameraSelector, preview, imageCapture)
            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
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
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val msg = "Photo capture succeeded: ${output.savedUri}"
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)
                    output.savedUri?.let {
                        PhotoRepository.photoUris.add(it)
                        Log.d(TAG, "Saved photo URI: $it")
                    }
                    _photoCount.value = _photoCount.value?.plus(1)
                    if (PhotoRepository.photoUris.size >= MAX_PHOTOS) {
                        Log.d(TAG, "Enough photos taken, starting upload")
                        sendPhotosToServer(context, PhotoRepository.photoUris)
                    }
                }
            }
        )
    }

    private fun sendPhotosToServer(context: Context, photoUris: List<Uri>) {
        buildRepository.uploadImages(
            context,
            photoUris,
            onSuccess = { responseBody ->
                val jsonObject = JSONObject(responseBody)
                val imageUrls = listOf(
                    jsonObject.optString("front_image"),
                    jsonObject.optString("back_image"),
                    jsonObject.optString("left_image"),
                    jsonObject.optString("right_image"),
                    jsonObject.optString("up_image")
                )
                val intent = Intent(context, BuildLoadingView::class.java).apply {
                    putStringArrayListExtra("imageUrls", ArrayList(imageUrls))
                    putExtra("json_response", responseBody)
                }
                context.startActivity(intent)
            },
            onFailure = { throwable ->
                Toast.makeText(context, "Photo upload error: ${throwable.message}", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "Photo upload error: ${throwable.message}")
            }
        )
    }

    companion object {
        private const val TAG = "CameraXApp"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val MAX_PHOTOS = 5
    }
}
