package com.blockmaker.fdland.data.source.remote.construct

import com.blockmaker.fdland.data.api.ConstructRetrofitInterface
import com.blockmaker.fdland.data.model.ConstructResponse
import com.greenfriends.zeroway.data.api.RetrofitClient
import okhttp3.MultipartBody
import retrofit2.Response

class ConstructDataSourceImpl : ConstructDataSource {

    private val constructService =
        RetrofitClient.getRetrofit()?.create(ConstructRetrofitInterface::class.java)

    override suspend fun setConstImg(
        accessToken: String,
        imgUrl: MultipartBody.Part
    ): Response<Void> {
        return constructService!!.setConstImg(accessToken, imgUrl)
    }

    override suspend fun getConstImg(
        accessToken: String,
        imgUrl: MultipartBody.Part
    ): Response<ConstructResponse> {
        return constructService!!.getConstImg(accessToken, imgUrl)
    }
}
