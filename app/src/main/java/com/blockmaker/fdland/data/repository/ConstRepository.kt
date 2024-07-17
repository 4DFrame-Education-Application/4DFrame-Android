package com.blockmaker.fdland.data.repository

import com.blockmaker.fdland.data.model.ConstructImgResponse
import com.blockmaker.fdland.data.source.remote.construct.ConstructDataSourceImpl
import okhttp3.MultipartBody
import retrofit2.Response

class ConstRepository(private val constructDataSourceImpl: ConstructDataSourceImpl) {

    suspend fun setConstImg(
        image_url: MultipartBody.Part
    ): Response<Void> {
        return constructDataSourceImpl.setConstImg(image_url)
    }

    suspend fun getConstImg(
        image_url: String
    ): Response<ConstructImgResponse> {
        return constructDataSourceImpl.getConstImg(image_url)
    }
}