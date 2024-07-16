package com.blockmaker.fdland.data.source.remote.construct

import com.blockmaker.fdland.data.api.ConstructRetrofitInterface
import com.blockmaker.fdland.data.model.ConstructImgResponse
import com.greenfriends.zeroway.data.api.RetrofitClient
import okhttp3.MultipartBody
import retrofit2.Response

class ConstructDataSourceImpl : ConstructDataSource {

    private val ConstructService =
        RetrofitClient.getRetrofit()?.create(ConstructRetrofitInterface::class.java)
    override suspend fun setConstImg(
        image_url: MultipartBody.Part
    ): Response<Void> {
        return ConstructService!!.setConstImg(image_url)
    }

    override suspend fun getConstImg(
        image_url: String
    ): Response<ConstructImgResponse> {
        return ConstructService!!.getConstImg(image_url)
    }
}
