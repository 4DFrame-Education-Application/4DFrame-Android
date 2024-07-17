package com.blockmaker.fdland.data.source.remote.construct

import com.blockmaker.fdland.data.api.ConstructRetrofitInterface
import com.blockmaker.fdland.data.api.RetrofitClient
import com.blockmaker.fdland.data.model.ConstructImgResponse
import okhttp3.MultipartBody
import retrofit2.Response

class ConstructDataSourceImpl : ConstructDataSource {

    private val constructService =
        RetrofitClient.getRetrofit()?.create(ConstructRetrofitInterface::class.java)
    override suspend fun setConstImg(
        image_url: MultipartBody.Part
    ): Response<Void> {
        return constructService!!.setConstImg(image_url)
    }

    override suspend fun getConstImg(
        image_url: String
    ): Response<ConstructImgResponse> {
        return constructService!!.getConstImg(image_url)
    }
}