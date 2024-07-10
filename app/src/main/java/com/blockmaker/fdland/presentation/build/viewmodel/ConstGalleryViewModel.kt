package com.blockmaker.fdland.presentation.build.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ConstGalleryViewModel : ViewModel() {

    private val _selectedImage = MutableLiveData<Uri>()
    val selectedImage: LiveData<Uri> get() = _selectedImage

    private val _navigateToNextPage = MutableLiveData<Boolean>()
    val navigateToNextPage: LiveData<Boolean> get() = _navigateToNextPage


    fun selectImage(uri: Uri) {
        _selectedImage.value = uri
        _navigateToNextPage.value = true // 한 장의 이미지만 선택해도 다음 페이지로 이동
    }

    fun resetNavigation() {
        _navigateToNextPage.value = false
    }
}
