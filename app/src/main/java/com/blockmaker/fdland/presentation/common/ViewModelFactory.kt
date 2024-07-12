package com.blockmaker.fdland.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.blockmaker.fdland.data.Repository.ConstRepository
import com.blockmaker.fdland.data.source.remote.construct.ConstructDataSourceImpl
import com.blockmaker.fdland.presentation.build.viewmodel.ConstGalleryViewModel

class ViewModelFactory(ConstRepository: Any?) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ConstGalleryViewModel::class.java) -> {
                ConstGalleryViewModel(
                    ConstRepository(
                        ConstructDataSourceImpl()
                    )
                ) as T
            }
            else -> throw IllegalArgumentException("Failed to create ViewModel: ${modelClass.name}")
        }
    }
}