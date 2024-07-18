package com.blockmaker.fdland.presentation.build.viewmodel

import android.content.ContentValues
import android.content.Context
import android.content.Intent
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
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blockmaker.fdland.data.source.remote.build.BuildDataSourceImpl
import com.blockmaker.fdland.presentation.build.view.BuildCameraActivity
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class BuildCameraViewModel : ViewModel() {

    private lateinit var cameraExecutor: ExecutorService
    private var imageCapture: ImageCapture? = null

    val photoCount = MutableLiveData(0)
    val loading = MutableLiveData(false)

    private val buildDataSource = BuildDataSourceImpl()

    private val capturedImages = mutableListOf<File>()

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
                Log.e(TAG, "Use case 바인딩 실패", exc)
            }

        }, ContextCompat.getMainExecutor(context))
    }

    fun takePhoto(context: Context) {
        val imageCapture = imageCapture ?: return

        loading.value = true

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
                    Log.e(TAG, "사진 캡처 실패: ${exc.message}", exc)
                    loading.value = false
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val msg = "사진 캡처 성공!"
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)

                    // 이미지 파일 경로 가져오기
                    val imageUri = output.savedUri ?: return
                    val filePath = getFilePathFromUri(context, imageUri) ?: return

                    capturedImages.add(File(filePath))

                    photoCount.value = (photoCount.value ?: 0) + 1
                    loading.value = false
                }
            }
        )
    }

    private fun getFilePathFromUri(context: Context, uri: android.net.Uri): String? {
        var filePath: String? = null
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        context.contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
            }
        }
        return filePath
    }

    fun uploadImages() {
        viewModelScope.launch {
            loading.value = true
            try {
                val parts = capturedImages.mapIndexed { index, file ->
                    val requestFile = RequestBody.create("image/jpeg".toMediaType(), file)
                    MultipartBody.Part.createFormData("image_$index", file.name, requestFile)
                }
                val response = buildDataSource.setBuildImg(
                    parts[0], parts[1], parts[2], parts[3], parts[4]
                )
                if (response.isSuccessful) {
                    Log.d(TAG, "이미지 전송 성공!")
                } else {
                    Log.e(TAG, "이미지 전송 실패: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "이미지 전송 중 예외 발생", e)
            } finally {
                loading.value = false
            }
        }
    }

    companion object {
        private const val TAG = "CameraXApp"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    }
}
