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

    // 선택된 이미지 URI를 저장
    private val _selectedImage = MutableLiveData<Uri?>()
    val selectedImage: LiveData<Uri?> get() = _selectedImage

    // 이미지 업로드 성공 여부와 URL을 저장
    private val _setConstImgIsSuccess = MutableLiveData<UploadResult?>()
    val setConstImgIsSuccess: LiveData<UploadResult?> get() = _setConstImgIsSuccess

    // 네비게이션 상태를 저장
    private val _navigateToNextPage = MutableLiveData<Boolean>()
    val navigateToNextPage: LiveData<Boolean> get() = _navigateToNextPage

    // 선택된 이미지 URI 설정 및 업로드 준비
    fun selectImage(uri: Uri, context: Context) {
        _selectedImage.value = uri
        _navigateToNextPage.value = true
        prepareAndSendImage(uri, context)
    }

    // 네비게이션 상태 초기화
    fun resetNavigation() {
        _navigateToNextPage.value = false
    }

    // 이미지 업로드를 준비하고 서버로 전송
    private fun prepareAndSendImage(uri: Uri, context: Context) {
        Toast.makeText(context, "사진을 서버로 전송 중... 서버로 보내고 있으니 잠시만 기다려주세요! ", Toast.LENGTH_LONG).show()
        viewModelScope.launch {
            try {
                // PathRepository를 사용하여 파일 경로를 가져옴
                val filePath = PathRepository.getRealPathFromURI(context, uri) ?: throw IllegalArgumentException("파일 경로를 가져오는 데 실패했습니다.")
                val file = File(filePath)
                val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val body = MultipartBody.Part.createFormData("composition_image", file.name, requestFile)

                // 서버에 이미지 업로드
                val response = repository.setConstImg(body)
                if (response.isSuccessful) {
                    val responseBody = response.body()?.string()
                    Log.d("ConstGalleryViewModel", "Image upload successful: $responseBody")

                    // 서버에서 반환된 URL 추출
                    val imageUrl = extractImageUrl(responseBody)
                    if (imageUrl.isNotEmpty()) {
                        _setConstImgIsSuccess.value = UploadResult(true, imageUrl)
                    } else {
                        Log.e("ConstGalleryViewModel", "이미지 URL이 비어있음: $responseBody")
                        _setConstImgIsSuccess.value = UploadResult(false, null)
                    }
                } else {
                    Log.e("ConstGalleryViewModel", "이미지 업로드 실패: ${response.errorBody()?.string()}")
                    _setConstImgIsSuccess.value = UploadResult(false, null)
                }
            } catch (e: Exception) {
                Log.e("ConstGalleryViewModel", "이미지 업로드 예외: ${e.message}", e)
                _setConstImgIsSuccess.value = UploadResult(false, null)
            }
        }
    }

    // 서버 응답에서 이미지 URL을 추출
    private fun extractImageUrl(responseBody: String?): String {
        return try {
            val jsonObject = JSONObject(responseBody ?: "{}")
            jsonObject.optString("image_url", "")
        } catch (e: Exception) {
            Log.e("ConstGalleryViewModel", "JSON 파싱 오류: ${e.message}")
            ""
        }
    }

    data class UploadResult(val isSuccess: Boolean, val imageUrl: String?)
}
