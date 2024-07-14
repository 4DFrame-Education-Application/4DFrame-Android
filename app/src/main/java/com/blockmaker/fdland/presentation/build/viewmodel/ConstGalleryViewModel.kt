package com.blockmaker.fdland.presentation.build.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blockmaker.fdland.data.repository.ConstRepository
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class ConstGalleryViewModel(private val constRepository: ConstRepository) : ViewModel() {

    private val _selectedImage = MutableLiveData<Uri>()
    val selectedImage: LiveData<Uri> get() = _selectedImage

    private val _navigateToNextPage = MutableLiveData<Boolean>()
    val navigateToNextPage: LiveData<Boolean> get() = _navigateToNextPage

    private val _setConstImgIsSuccess = MutableLiveData<Boolean?>()
    val setConstImgIsSuccess: LiveData<Boolean?> get() = _setConstImgIsSuccess

    fun selectImage(uri: Uri) {
        _selectedImage.value = uri
    }

    fun setConstImg(context: Context, uri: Uri) {
        viewModelScope.launch {
            try {
                val file = File(uri.path ?: "")
                val requestFile = RequestBody.create(
                    context.contentResolver.getType(uri)?.toMediaTypeOrNull(),
                    file
                )
                val body = MultipartBody.Part.createFormData("imgUrl", file.name, requestFile)

                val response = constRepository.setConstImg(body)
                if (response.isSuccessful) {
                    _setConstImgIsSuccess.value = true
                    Log.d("Upload", "Success: ${response.body()}")
                    _navigateToNextPage.value = true
                } else {
                    _setConstImgIsSuccess.value = false
                    Log.d("Upload", "Failure: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _setConstImgIsSuccess.value = false
                Log.e("Upload", "Error: ${e.localizedMessage}", e)
            }
        }
    }

    fun resetNavigation() {
        _navigateToNextPage.value = false
    }
}
