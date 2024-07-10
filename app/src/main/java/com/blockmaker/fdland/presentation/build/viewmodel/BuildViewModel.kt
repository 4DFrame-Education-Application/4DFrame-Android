package com.blockmaker.fdland.presentation.build.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blockmaker.fdland.presentation.build.view.BuildCameraActivity
import com.blockmaker.fdland.presentation.build.view.BuildGalleryActivity
import com.blockmaker.fdland.presentation.main.MainActivity

class BuildViewModel : ViewModel() {

    private val _navigateToActivity = MutableLiveData<Class<*>>()
    val navigateToActivity: LiveData<Class<*>> get() = _navigateToActivity

    fun onPreviousButtonClick() {
        _navigateToActivity.value = MainActivity::class.java
    }

    fun onCameraButtonClick() {
        _navigateToActivity.value = BuildCameraActivity::class.java
    }

    fun onGalleryButtonClick() {
        _navigateToActivity.value = BuildGalleryActivity::class.java
    }
}
