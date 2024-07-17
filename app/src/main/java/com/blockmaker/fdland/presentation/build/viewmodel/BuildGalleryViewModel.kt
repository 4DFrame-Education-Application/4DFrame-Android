package com.blockmaker.fdland.presentation.build.viewmodel

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blockmaker.fdland.data.model.ResultList
import com.blockmaker.fdland.data.repository.BuildRepository
import com.blockmaker.fdland.data.repository.PhotoRepository
import com.blockmaker.fdland.presentation.build.view.BuildLoadingView
import org.json.JSONException
import org.json.JSONObject

class BuildGalleryViewModel : ViewModel() {
//
//    private val _selectedImages = MutableLiveData<List<Uri>>(emptyList())
//    val selectedImages: LiveData<List<Uri>> get() = _selectedImages
//
//    private val _navigateToNextPage = MutableLiveData<Boolean>()
//    val navigateToNextPage: LiveData<Boolean> get() = _navigateToNextPage
//
//    private val maxImageCount = 5
//
//    fun selectImage(uri: Uri) {
//        val currentImages = _selectedImages.value ?: emptyList()
//        if (currentImages.size < maxImageCount) {
//            _selectedImages.value = currentImages + uri
//            if (currentImages.size + 1 == maxImageCount) {
//                _navigateToNextPage.value = true
//            }
//        }
//    }
//
//    fun resetNavigation() {
//        _navigateToNextPage.value = false
//    }
//}
private val _selectedImages = MutableLiveData<List<Uri>>(emptyList())
    val selectedImages: LiveData<List<Uri>> get() = _selectedImages

    private val _navigateToNextPage = MutableLiveData<Boolean>()
    val navigateToNextPage: LiveData<Boolean> get() = _navigateToNextPage

    private val _imageUrls = MutableLiveData<List<String>>()
    val imageUrls: LiveData<List<String>> get() = _imageUrls

    private val maxImageCount = 5
    private val buildRepository = BuildRepository()

    fun selectImage(uri: Uri, context: Context) {
        val currentImages = _selectedImages.value ?: emptyList()
        if (currentImages.size < maxImageCount) {
            _selectedImages.value = currentImages + uri
            PhotoRepository.photoUris.add(uri)

            if (currentImages.size + 1 == maxImageCount) {
                uploadImagesToServer(context)
            }
        }
    }

    fun resetNavigation() {
        _navigateToNextPage.value = false
    }

    private fun uploadImagesToServer(context: Context) {
        buildRepository.uploadImages(
            context,
            PhotoRepository.photoUris,
            onSuccess = { responseBody ->
                try {
                    parseResponse(context, responseBody)
                } catch (e: JSONException) {
                    Toast.makeText(context, "JSON Parse Error", Toast.LENGTH_SHORT).show()
                }
            },
            onFailure = { error ->
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun parseResponse(context: Context, responseBody: String) {
        val jsonObject = JSONObject(responseBody)

        val imageUrls = listOf(
            jsonObject.optString("front_image"),
            jsonObject.optString("back_image"),
            jsonObject.optString("left_image"),
            jsonObject.optString("right_image"),
            jsonObject.optString("up_image")
        )

        _imageUrls.value = imageUrls

        // 로딩 화면으로 이동
        val intent = Intent(context, BuildLoadingView::class.java).apply {
            putStringArrayListExtra("imageUrls", ArrayList(imageUrls))
            putExtra("json_response", responseBody) // 전체 JSON 응답 전달
        }
        context.startActivity(intent)
    }
}
