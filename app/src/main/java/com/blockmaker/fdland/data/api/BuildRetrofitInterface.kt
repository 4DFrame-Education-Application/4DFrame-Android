package com.blockmaker.fdland.data.api

import com.blockmaker.fdland.data.model.BuildResultResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface BuildRetrofitInterface {

    /** 카메라 페이지: 이미지 보내기 **/
    @Multipart
    @POST("/results/upload")
    suspend fun setBuildImg(
        @Part front_image: MultipartBody.Part,
        @Part back_image: MultipartBody.Part,
        @Part left_image: MultipartBody.Part,
        @Part right_image: MultipartBody.Part,
        @Part up_image: MultipartBody.Part
    ): Response<Void>

    /** 카메라 페이지: 이미지 받기 **/
    @GET("/results/upload")
    suspend fun getBuild(
        @Query("accuracy")
        accuracy: String?,
        @Query("name")
        name: String?,
        @Query("rate")
        rate: String?,
        right_image: String,
        up_image: String,
        count: Int
    ): Response<BuildResultResponse>


}