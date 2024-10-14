package com.blockmaker.fdland.data.source.remote.construct

import com.blockmaker.fdland.data.api.ConstructRetrofitInterface
import com.blockmaker.fdland.data.api.RetrofitClient
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response

class ConstructDataSourceImpl : ConstructDataSource {

    private val constructService: ConstructRetrofitInterface by lazy {
        RetrofitClient.getRetrofit().create(ConstructRetrofitInterface::class.java)
    }

    override suspend fun setConstImg(token: String, image_url: MultipartBody.Part): Response<ResponseBody> {
        return constructService.setConstImg(token, image_url)  // Pass the token to the service
    }
}