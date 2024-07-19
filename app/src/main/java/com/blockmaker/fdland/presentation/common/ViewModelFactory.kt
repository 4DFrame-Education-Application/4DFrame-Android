package com.blockmaker.fdland.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.blockmaker.fdland.data.repository.ConstRepository
import com.blockmaker.fdland.presentation.build.viewmodel.ConstGalleryViewModel
import com.blockmaker.fdland.presentation.build.viewmodel.ConstCamViewModel

class ViewModelFactory(private val constRepository: ConstRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ConstGalleryViewModel::class.java) -> {
                ConstGalleryViewModel(constRepository) as T
            }

            modelClass.isAssignableFrom(ConstCamViewModel::class.java) -> {
                ConstCamViewModel(constRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
