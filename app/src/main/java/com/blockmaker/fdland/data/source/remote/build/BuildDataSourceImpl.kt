package com.blockmaker.fdland.data.source.remote.build

import com.blockmaker.fdland.data.api.BuildRetrofitInterface
import com.blockmaker.fdland.data.api.RetrofitClient
import com.blockmaker.fdland.data.model.BuildResultResponse
import okhttp3.MultipartBody
import retrofit2.Response

class BuildDataSourceImpl : BuildDataSource {

    private val buildService =
        RetrofitClient.getRetrofit()?.create(BuildRetrofitInterface::class.java)

    override suspend fun setBuildImg(
        front_image: MultipartBody.Part,
        back_image: MultipartBody.Part,
        left_image: MultipartBody.Part,
        right_image: MultipartBody.Part,
        up_image: MultipartBody.Part
    ): Response<Void> {
        return buildService!!.setBuildImg(front_image, back_image, left_image, right_image, up_image)
    }

    override suspend fun getBuild(
        front_image: String,
        back_image: String,
        left_image: String,
        right_image: String,
        up_image: String,
        count: Int
    ): Response<BuildResultResponse> {
        return buildService!!.getBuild(front_image, back_image, left_image, right_image, up_image, count)
    }
}
