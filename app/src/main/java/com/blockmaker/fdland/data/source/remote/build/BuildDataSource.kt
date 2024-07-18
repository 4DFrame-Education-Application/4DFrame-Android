package com.blockmaker.fdland.data.source.remote.build

import com.blockmaker.fdland.data.model.BuildResultResponse
import okhttp3.MultipartBody
import retrofit2.Response

interface BuildDataSource {

    suspend fun setBuildImg(
        front_image: MultipartBody.Part,
        back_image: MultipartBody.Part,
        left_image: MultipartBody.Part,
        right_image: MultipartBody.Part,
        up_image: MultipartBody.Part
    ): Response<Void>

    suspend fun getBuild(
        front_image: String,
        back_image: String,
        left_image: String,
        right_image: String,
        up_image: String,
        count: Int
    ): Response<BuildResultResponse>
}
