package com.blockmaker.fdland.data.api

import com.blockmaker.fdland.data.model.ConstructResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Query

interface ConstructRetrofitInterface {
    /**
     * 구성놀이 이미지 전송 API
     */
    @Multipart
    @POST("construct/upload")
    suspend fun setConstImg(
        @Header("Authorization") accessToken: String,
        @Query("imgUrl") imgUrl: MultipartBody.Part
    ): Response<Void>

    /**
     * 구성놀이 분석 이미지 조회 API
     */
    @Multipart
    @GET("construct/analyze")
    suspend fun getConstImg(
        @Header("Authorization") accessToken: String,
        @Query("imgUrl") imgUrl: MultipartBody.Part
    ): Response<ConstructResponse>
}


