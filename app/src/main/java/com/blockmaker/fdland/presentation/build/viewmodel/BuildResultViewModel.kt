package com.blockmaker.fdland.presentation.build.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blockmaker.fdland.data.model.BuildResultResponse
import com.blockmaker.fdland.data.repository.BuildRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import java.io.File

class BuildResultViewModel(private val repository: BuildRepository) : ViewModel() {

    private val _buildResponse = MutableLiveData<BuildResultResponse?>()
    val buildResponse: LiveData<BuildResultResponse?> get() = _buildResponse

    private val _navigateToBuildActivity = MutableLiveData<Boolean>()
    val navigateToBuildActivity: LiveData<Boolean> get() = _navigateToBuildActivity

    private val _navigateToMainActivity = MutableLiveData<Boolean>()
    val navigateToMainActivity: LiveData<Boolean> get() = _navigateToMainActivity

    fun getBuildImg(
        frontImage: Uri,
        backImage: Uri,
        leftImage: Uri,
        rightImage: Uri,
        upImage: Uri
    ) {
        viewModelScope.launch {
            try {
                val frontRequestBody = createImageRequestBody(frontImage)
                val backRequestBody = createImageRequestBody(backImage)
                val leftRequestBody = createImageRequestBody(leftImage)
                val rightRequestBody = createImageRequestBody(rightImage)
                val upRequestBody = createImageRequestBody(upImage)

                val frontPart = MultipartBody.Part.createFormData("front_image", "front.jpg", frontRequestBody)
                val backPart = MultipartBody.Part.createFormData("back_image", "back.jpg", backRequestBody)
                val leftPart = MultipartBody.Part.createFormData("left_image", "left.jpg", leftRequestBody)
                val rightPart = MultipartBody.Part.createFormData("right_image", "right.jpg", rightRequestBody)
                val upPart = MultipartBody.Part.createFormData("up_image", "up.jpg", upRequestBody)

                val response = repository.getBuildImg(frontPart, backPart, leftPart, rightPart, upPart)
                if (response.isSuccessful) {
                    _buildResponse.value = response.body().toString()
                } else {
                    _buildResponse.value = null // Handle error case
                }
            } catch (e: HttpException) {
                // Handle HttpException
                _buildResponse.value = null
            } catch (e: Exception) {
                // Handle other exceptions
                _buildResponse.value = null
            }
        }
    }

    private fun createImageRequestBody(uri: Uri): RequestBody {
        val file = File(uri.path ?: "")
        return RequestBody.create("image/jpeg".toMediaType(), file)
    }

    fun onPrevButtonClicked() {
        _navigateToBuildActivity.value = true
    }

    fun onMainButtonClicked() {
        _navigateToMainActivity.value = true
    }

    fun onNavigationHandled() {
        _navigateToBuildActivity.value = false
        _navigateToMainActivity.value = false
    }
}
