package com.blockmaker.fdland.data.api

import com.blockmaker.fdland.data.model.BuildResultResponse
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface BuildRetrofitInterface {

    // EndPoint 수정/** 카메라 페이지: 이미지 보내기 **/
    @Multipart
    @POST("/results/upload")
    fun uploadMultipleImages(
        @Part frontImage: MultipartBody.Part,
        @Part backImage: MultipartBody.Part,
        @Part leftImage: MultipartBody.Part,
        @Part rightImage: MultipartBody.Part,
        @Part upImage: MultipartBody.Part
    ): Call<ResponseBody>


}