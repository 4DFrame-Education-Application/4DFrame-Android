package com.blockmaker.fdland.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.blockmaker.fdland.data.repository.BuildRepository
import com.blockmaker.fdland.data.repository.ConstRepository
import com.blockmaker.fdland.data.source.remote.build.BuildDataSourceImpl
import com.blockmaker.fdland.data.source.remote.construct.ConstructDataSourceImpl
import com.blockmaker.fdland.presentation.build.viewmodel.BuildCameraViewModel
import com.blockmaker.fdland.presentation.build.viewmodel.ConstGalleryViewModel
import com.blockmaker.fdland.presentation.build.viewmodel.ConstCamViewModel

class ViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ConstGalleryViewModel::class.java) -> {
                ConstGalleryViewModel(
                    ConstRepository(
                        ConstructDataSourceImpl()
                    )
                ) as T
            }
            modelClass.isAssignableFrom(ConstCamViewModel::class.java) -> {
                ConstCamViewModel(
                    ConstRepository(
                        ConstructDataSourceImpl()
                    )
                ) as T
            }
            else -> throw IllegalArgumentException("Failed to create ViewModel: ${modelClass.name}")
        }
    }
}
