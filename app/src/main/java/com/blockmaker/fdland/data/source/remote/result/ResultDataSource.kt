package com.blockmaker.fdland.data.source.remote.result

import com.blockmaker.fdland.data.model.ResultList
import com.blockmaker.fdland.data.model.ResultResponse
import retrofit2.Response

interface ResultDataSource {
    suspend fun getResult(
        name: String,
        accuracy: String?,
        rate: String?,
        imageUrl: String?
    ): Response<ResultResponse>
}