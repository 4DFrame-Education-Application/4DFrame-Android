package com.blockmaker.fdland.data.source.remote.construct

import com.blockmaker.fdland.data.model.ConstructResponse
import okhttp3.MultipartBody
import retrofit2.Response

interface ConstructDataSource {

    suspend fun setConstImg(
        image_url: MultipartBody.Part
    ): Response<Void>

    suspend fun getConstImg(
        image_url: MultipartBody.Part
    ): Response<ConstructResponse>
}