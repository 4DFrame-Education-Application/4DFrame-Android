package com.blockmaker.Blockmaker.presentation.build.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blockmaker.Blockmaker.presentation.build.ConstGalleryActivity
import com.blockmaker.Blockmaker.presentation.build.view.ConstCamActivity
import com.blockmaker.Blockmaker.presentation.main.MainActivity

class ConstViewModel : ViewModel() {

    private val _navigateToActivity = MutableLiveData<Class<*>>()
    val navigateToActivity: LiveData<Class<*>> get() = _navigateToActivity

    fun onPreviousButtonClick() {
        _navigateToActivity.value = MainActivity::class.java
    }

    fun onCameraButtonClick() {
        _navigateToActivity.value = ConstCamActivity::class.java
    }

    fun onGalleryButtonClick() {
        _navigateToActivity.value = ConstGalleryActivity::class.java
    }
}
