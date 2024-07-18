package com.blockmaker.fdland.presentation.build.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blockmaker.fdland.data.repository.BuildRepository
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class BuildGalleryViewModel(private val repository: BuildRepository) : ViewModel() {

    private val _selectedImages = MutableLiveData<List<Uri>>(emptyList())
    val selectedImages: LiveData<List<Uri>> get() = _selectedImages

    private val _navigateToNextPage = MutableLiveData<Boolean>()
    val navigateToNextPage: LiveData<Boolean> get() = _navigateToNextPage

    private val _uploadStatus = MutableLiveData<Boolean>()
    val uploadStatus: LiveData<Boolean> get() = _uploadStatus

    private val maxImageCount = 5

    fun selectImage(uri: Uri, index: Int) {
        val currentImages = _selectedImages.value ?: emptyList()
        if (currentImages.size < maxImageCount) {
            val updatedImages = currentImages.toMutableList().apply {
                if (size > index) {
                    set(index, uri)
                } else {
                    add(uri)
                }
            }
            _selectedImages.value = updatedImages
            if (updatedImages.size == maxImageCount) {
                uploadImages(updatedImages)
            }
        }
    }

    private fun uploadImages(uris: List<Uri>) {
        viewModelScope.launch {
            try {
                val parts = uris.mapIndexed { index, uri ->
                    val file = File(uri.path ?: "")
                    val requestFile = RequestBody.create("image/jpeg".toMediaType(), file)
                    MultipartBody.Part.createFormData("image_$index", file.name, requestFile)
                }
                val response = repository.setBuildImg(
                    parts[0], parts[1], parts[2], parts[3], parts[4]
                )
                _uploadStatus.value = response.isSuccessful
            } catch (e: Exception) {
                _uploadStatus.value = false
            }
        }
    }

    fun resetNavigation() {
        _navigateToNextPage.value = false
    }
}
