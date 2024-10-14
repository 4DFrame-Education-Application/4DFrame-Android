package com.blockmaker.fdland.presentation.build.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blockmaker.fdland.data.repository.ConstRepository
import com.blockmaker.fdland.data.repository.PathRepository
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File

class ConstGalleryViewModel(private val repository: ConstRepository) : ViewModel() {

    private val _selectedImage = MutableLiveData<Uri?>()
    val selectedImage: LiveData<Uri?> get() = _selectedImage

    private val _setConstImgIsSuccess = MutableLiveData<UploadResult?>()
    val setConstImgIsSuccess: LiveData<UploadResult?> get() = _setConstImgIsSuccess

    private val _navigateToNextPage = MutableLiveData<Boolean>()
    val navigateToNextPage: LiveData<Boolean> get() = _navigateToNextPage

    fun selectImage(uri: Uri, token: String, context: Context) {
        _selectedImage.value = uri
        _navigateToNextPage.value = true
        val token = getTokenFromPreferences(context)  // SharedPreferences에서 토큰 가져오기
        prepareAndSendImage(uri, token, context)
    }

    fun resetNavigation() {
        _navigateToNextPage.value = false
    }

    private fun prepareAndSendImage(uri: Uri, token: String, context: Context) {
        Toast.makeText(context, "사진을 서버로 전송 중... 서버로 보내고 있으니 잠시만 기다려주세요!", Toast.LENGTH_LONG).show()
        viewModelScope.launch {
            try {
                val filePath = PathRepository.getRealPathFromURI(context, uri)
                    ?: throw IllegalArgumentException("파일 경로를 가져오는 데 실패했습니다.")
                val file = File(filePath)
                val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val body = MultipartBody.Part.createFormData("composition_image", file.name, requestFile)

                val response = repository.setConstImg(token, body)
                if (response.isSuccessful) {
                    val responseBody = response.body()?.string()
                    Log.d(TAG, "Image upload successful: $responseBody")

                    val imageUrl = extractImageUrl(responseBody)
                    if (imageUrl.isNotEmpty()) {
                        _setConstImgIsSuccess.value = UploadResult(true, imageUrl)
                        _navigateToNextPage.value = true
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

    private fun getTokenFromPreferences(context: Context): String {
        val sharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("X-AUTH-TOKEN", "") ?: ""
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
        private const val TAG = "ConstGalleryViewModel"
    }
}