package com.blockmaker.fdland.data.api

import com.blockmaker.fdland.data.model.ResultResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ResultRetrofitInterface {
    @GET("results/upload")
    fun getResult(
        @Query("name") name: String?,
        @Query("accuracy") accuracy: String?,
        @Query("rate") rate: String?,
        @Query("imageUrl") imageUrl: String?
    ): Call<ResultResponse>
}
