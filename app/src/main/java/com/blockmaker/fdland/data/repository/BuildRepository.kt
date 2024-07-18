package com.blockmaker.fdland.data.repository

import com.blockmaker.fdland.data.model.BuildResultResponse
import com.blockmaker.fdland.data.source.remote.build.BuildDataSourceImpl
import okhttp3.MultipartBody
import retrofit2.Response

class BuildRepository(private val buildDataSourceImpl: BuildDataSourceImpl) {
    suspend fun setBuildImg(
        front_img: MultipartBody.Part,
        back_image: MultipartBody.Part,
        left_image: MultipartBody.Part,
        right_image: MultipartBody.Part,
        up_image: MultipartBody.Part
    ): Response<Void> {
        return buildDataSourceImpl.setBuildImg(front_img, back_image, left_image, right_image, up_image)
    }

    suspend fun getBuildImg(
        front_img: String,
        back_image: String,
        left_image: String,
        right_image: String,
        up_image: String
    ): Response<BuildResultResponse> {
        return buildDataSourceImpl.getBuildImg(front_img, back_image, left_image, right_image, up_image)
    }

}