package com.blockmaker.fdland.data.api

import com.blockmaker.fdland.data.model.ResultList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ResultRetrofitInterface {
    @GET("results/upload")
    suspend fun getResult(
        @Header("Authorization") accessToken: String,
        @Query("name") name: String? = null,
        @Query("accuracy") accuracy: String? = null,
        @Query("imageUrl") imageUrl: String? = null
    ): Call<List<ResultList>>
}
