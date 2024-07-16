package com.blockmaker.fdland.presentation.build.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blockmaker.fdland.data.model.ResultList

class BuildGalleryViewModel : ViewModel() {

    private val _selectedImages = MutableLiveData<List<Uri>>(emptyList())
    val selectedImages: LiveData<List<Uri>> get() = _selectedImages

    private val _navigateToNextPage = MutableLiveData<Boolean>()
    val navigateToNextPage: LiveData<Boolean> get() = _navigateToNextPage

    private val maxImageCount = 5

    fun selectImage(uri: Uri) {
        val currentImages = _selectedImages.value ?: emptyList()
        if (currentImages.size < maxImageCount) {
            _selectedImages.value = currentImages + uri
            if (currentImages.size + 1 == maxImageCount) {
                _navigateToNextPage.value = true
            }
        }
    }

    fun resetNavigation() {
        _navigateToNextPage.value = false
    }
}
