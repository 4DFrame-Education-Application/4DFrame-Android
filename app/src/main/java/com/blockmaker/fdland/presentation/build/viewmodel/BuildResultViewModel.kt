package com.blockmaker.fdland.presentation.build.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BuildResultViewModel : ViewModel() {

    private val _navigateToBuildActivity = MutableLiveData<Boolean>()
    val navigateToBuildActivity: LiveData<Boolean> get() = _navigateToBuildActivity

    private val _navigateToMainActivity = MutableLiveData<Boolean>()
    val navigateToMainActivity: LiveData<Boolean> get() = _navigateToMainActivity

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
