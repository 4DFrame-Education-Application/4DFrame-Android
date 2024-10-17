package com.blockmaker.fdland.data.api

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface BuildRetrofitInterface {

    /** 카메라 페이지: 이미지 보내기 **/
    @Multipart
    @POST("api/block/upload")
    fun uploadMultipleImages(
        @Header("X-AUTH-TOKEN") token: String,
        @Part frontImage: MultipartBody.Part,
        @Part backImage: MultipartBody.Part,
        @Part leftImage: MultipartBody.Part,
        @Part rightImage: MultipartBody.Part,
        @Part upImage: MultipartBody.Part
    ): Call<ResponseBody>


}