package com.blockmaker.fdland.data.Repository

import com.blockmaker.fdland.data.model.ConstructResponse
import com.blockmaker.fdland.data.source.remote.construct.ConstructDataSourceImpl
import okhttp3.MultipartBody
import retrofit2.Response

class ConstRepository(private val constructDataSourceImpl: ConstructDataSourceImpl) {

    suspend fun setConstImg(
        accessToken: String,
        imgUrl: MultipartBody.Part
    ): Response<Void> {
        return constructDataSourceImpl.setConstImg(accessToken, imgUrl)
    }

    suspend fun getConstImg(
        accessToken: String,
        imgUrl: MultipartBody.Part
    ): Response<ConstructResponse> {
        return constructDataSourceImpl.getConstImg(accessToken, imgUrl)
    }

}