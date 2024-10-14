package com.blockmaker.fdland.data.source.remote.construct

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response

interface ConstructDataSource {
    suspend fun setConstImg(
        token: String,  // Add token parameter
        image_url: MultipartBody.Part
    ): Response<ResponseBody>
}