package com.blockmaker.fdland.presentation.build.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BuildResultViewModel : ViewModel() {

    private val _navigateToBuildActivity = MutableLiveData<Boolean>()
    val navigateToBuildActivity: LiveData<Boolean> get() = _navigateToBuildActivity

    private val _navigateToMainActivity = MutableLiveData<Boolean>()
    val navigateToMainActivity: LiveData<Boolean> get() = _navigateToMainActivity

    private val _imageUrls = MutableLiveData<List<String>>()
    val imageUrls: LiveData<List<String>> get() = _imageUrls

    private val _jsonResponse = MutableLiveData<String>()
    val jsonResponse: LiveData<String> get() = _jsonResponse

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

    fun setImageUrls(urls: List<String>) {
        _imageUrls.value = urls
    }

    fun setJsonResponse(response: String) {
        _jsonResponse.value = response
    }
}
