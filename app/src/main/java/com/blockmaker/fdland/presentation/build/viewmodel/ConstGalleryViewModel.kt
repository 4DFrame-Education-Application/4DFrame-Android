package com.blockmaker.fdland.presentation.build.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blockmaker.fdland.data.Repository.ConstRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class ConstGalleryViewModel(private val constRepository: ConstRepository) : ViewModel() {

    private val _selectedImage = MutableLiveData<Uri>()
    val selectedImage: LiveData<Uri> get() = _selectedImage

    private val _navigateToNextPage = MutableLiveData<Boolean>()
    val navigateToNextPage: LiveData<Boolean> get() = _navigateToNextPage

    private val _setConstImgIsSuccess = MutableLiveData<Boolean?>(null)
    val setConstImgIsSuccess: LiveData<Boolean?> get() = _setConstImgIsSuccess

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun selectImage(uri: Uri) {
        _selectedImage.value = uri
        _navigateToNextPage.value = true // 한 장의 이미지만 선택해도 다음 페이지로 이동
    }

    fun setConstImg(accessToken: String, imgUrl: MultipartBody.Part) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = constRepository.setConstImg(accessToken, imgUrl)
                if (response.isSuccessful) {
                    _setConstImgIsSuccess.value = true
                    Log.d("CONST/IMGPOST/T", response.body().toString())
                } else {
                    _setConstImgIsSuccess.value = false
                    Log.d("CONST/IMGPOST/F", response.errorBody()?.string()!!)
                }
            } catch (e: Exception) {
                _setConstImgIsSuccess.value = false
                Log.d("CONST/IMGPOST/E", "Error during image upload: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetNavigation() {
        _navigateToNextPage.value = false
    }
}
