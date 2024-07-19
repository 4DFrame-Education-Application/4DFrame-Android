package com.blockmaker.fdland.data.api

import com.blockmaker.fdland.data.model.ConstructImgResponse
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ConstructRetrofitInterface {

//    @Multipart
//    @POST("/composition/composition-upload")
//    suspend fun setConstImg(
//        @Part image_url: MultipartBody.Part
//    ): Response<Void>

    @Multipart
    @POST("/composition/composition-upload")
    suspend fun setConstImg(
        @Part image_url: MultipartBody.Part
    ): Response<ResponseBody>// Void -> RespnseBody (Json 반환을 위해)


    @GET("/composition/composition-upload")
    suspend fun getConstImg(
        @Query("image_url") image_url: String
    ): Response<ConstructImgResponse>
}
