//package com.blockmaker.fdland.presentation.build.viewmodel
//
//import android.content.ContentResolver
//import android.content.Context
//import android.content.Intent
//import android.database.Cursor
//import android.net.Uri
//import android.provider.MediaStore
//import android.util.Log
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.blockmaker.fdland.data.api.ConstructRetrofitInterface
//import com.blockmaker.fdland.data.api.RetrofitClient
//import com.blockmaker.fdland.presentation.build.view.ConstLoadingView
//import com.greenfriends.zeroway.data.api.RetrofitClient
//import kotlinx.coroutines.launch
//import okhttp3.MediaType.Companion.toMediaTypeOrNull
//import okhttp3.MultipartBody
//import okhttp3.RequestBody.Companion.asRequestBody
//import okhttp3.ResponseBody
//import org.json.JSONObject
//import retrofit2.Response
//import java.io.File
//
//class ConstGalleryViewModel : ViewModel() {
//
//    private val _selectedImage = MutableLiveData<Uri?>()
//    val selectedImage: LiveData<Uri?> get() = _selectedImage
//
//    private val _navigateToNextPage = MutableLiveData<Boolean>()
//    val navigateToNextPage: LiveData<Boolean?> get() = _navigateToNextPage
//
//    private val _setConstImgIsSuccess = MutableLiveData<Boolean?>()
//    val setConstImgIsSuccess: LiveData<Boolean?> get() = _setConstImgIsSuccess
//
//    fun selectImage(uri: Uri, context: Context) {
//        _selectedImage.value = uri
//        prepareAndSendImage(uri, context)
//    }
//
//    fun resetNavigation() {
//        _navigateToNextPage.value = false
//    }
//
//    private fun prepareAndSendImage(uri: Uri, context: Context) {
//        viewModelScope.launch {
//            try {
//                val response = uploadImageToServer(uri, context)
//                if (response.isSuccessful) {
//                    val responseBody = response.body()?.string()
//                    Log.d("ConstGalleryViewModel", "Image upload successful: $responseBody")
//
//                    val imageUrl = extractImageUrl(responseBody)
//                    if (imageUrl.isNotEmpty()) {
//                        moveToLoadingView(context, imageUrl)
//                        _setConstImgIsSuccess.value = true
//                    } else {
//                        Log.e("ConstGalleryViewModel", "이미지 URL이 비어있음: $responseBody")
//                        _setConstImgIsSuccess.value = false
//                    }
//                } else {
//                    Log.e("ConstGalleryViewModel", "이미지 업로드 실패: ${response.errorBody()?.string()}")
//                    _setConstImgIsSuccess.value = false
//                }
//            } catch (e: Exception) {
//                Log.e("ConstGalleryViewModel", "이미지 업로드 예외: ${e.message}", e)
//                _setConstImgIsSuccess.value = false
//            }
//        }
//    }
//
//    private suspend fun uploadImageToServer(uri: Uri, context: Context): Response<ResponseBody> {
//        val filePath = getRealPathFromURI(context, uri)
//        if (filePath != null) {
//            val file = File(filePath)
//            val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
//            val body = MultipartBody.Part.createFormData("composition_image", file.name, requestFile)
//
//            Log.d("ConstGalleryViewModel", "전송할 파일 경로: ${file.absolutePath}")
//            Log.d("ConstGalleryViewModel", "전송할 파일 이름: ${file.name}")
//
//            val apiService = RetrofitClient.instance.create(ConstructRetrofitInterface::class.java)
//            return apiService.uploadSingleImage(body)
//        } else {
//            throw IllegalArgumentException("파일 경로를 가져오는 데 실패했습니다.")
//        }
//    }
//
//    private fun extractImageUrl(responseBody: String?): String {
//        return try {
//            val jsonObject = JSONObject(responseBody ?: "{}")
//            jsonObject.optString("image_url", "")
//        } catch (e: Exception) {
//            Log.e("ConstGalleryViewModel", "JSON 파싱 오류: ${e.message}")
//            ""
//        }
//    }
//
//    private fun moveToLoadingView(context: Context, imageUrl: String) {
//        val intent = Intent(context, ConstLoadingView::class.java).apply {
//            putExtra("image_url", imageUrl)
//        }
//        context.startActivity(intent)
//    }
//
//    private fun getRealPathFromURI(context: Context, uri: Uri): String? {
//        val contentResolver: ContentResolver = context.contentResolver
//        val projection = arrayOf(MediaStore.Images.Media.DATA)
//        val cursor: Cursor? = contentResolver.query(uri, projection, null, null, null)
//
//        return cursor?.use {
//            if (it.moveToFirst()) {
//                val idx: Int = it.getColumnIndex(MediaStore.Images.Media.DATA)
//                if (idx != -1) {
//                    it.getString(idx)
//                } else {
//                    getFileFromUri(context, uri)
//                }
//            } else {
//                null
//            }
//        } ?: getFileFromUri(context, uri)
//    }
//
//    private fun getFileFromUri(context: Context, uri: Uri): String? {
//        return try {
//            val file = File(uri.path)
//            if (file.exists()) file.absolutePath else null
//        } catch (e: Exception) {
//            null
//        }
//    }
//}
package com.blockmaker.fdland.presentation.build.viewmodel

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blockmaker.fdland.data.repository.ConstRepository
import com.blockmaker.fdland.presentation.build.view.ConstLoadingView
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import java.io.File

class ConstGalleryViewModel(private val repository: ConstRepository) : ViewModel() {

    private val _selectedImage = MutableLiveData<Uri?>()
    val selectedImage: LiveData<Uri?> get() = _selectedImage

    private val _navigateToNextPage = MutableLiveData<Boolean>()
    val navigateToNextPage: LiveData<Boolean> get() = _navigateToNextPage

    private val _setConstImgIsSuccess = MutableLiveData<Boolean?>()
    val setConstImgIsSuccess: LiveData<Boolean?> get() = _setConstImgIsSuccess


    fun resetNavigation() {
        _navigateToNextPage.value = false
    }

    fun selectImage(uri: Uri, context: Context) {
        _selectedImage.value = uri
        prepareAndSendImage(uri, context)
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
            Log.e("ConstGalleryViewModel", "JSON 파싱 오류: ${e.message}")
            ""
        }
    }

    private fun moveToLoadingView(context: Context, imageUrl: String) {
        val intent = Intent(context, ConstLoadingView::class.java).apply {
            putExtra("image_url", imageUrl)
        }
        context.startActivity(intent)
    }
}
