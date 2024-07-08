package com.blockmaker.Blockmaker.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface ResultRetrofitInterface {

    /** 결과: 이미지의 정보 받기 **/

    @GET("results/upload")
    suspend fun getResult(
        @Query("name") name: String? = null,
        @Query("accuracy") accuracy: String? = null,
        @Query("rate") rate: String? = null,
        @Query("imageUrl") imageUrl: String? = null
    )
}