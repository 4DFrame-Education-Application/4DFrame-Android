package com.blockmaker.fdland.data.source.remote.result

import com.blockmaker.fdland.data.model.ResultList
import com.blockmaker.fdland.data.model.ResultResponse
import okhttp3.MultipartBody
import retrofit2.Response

interface ResultDataSource {

    suspend fun setConstImg(
        image_url: MultipartBody.Part
    ): Response<Void>
    suspend fun getResult(
        name: String,
        accuracy: String?,
        rate: String?,
        imageUrl: String?
    ): Response<ResultResponse>
}