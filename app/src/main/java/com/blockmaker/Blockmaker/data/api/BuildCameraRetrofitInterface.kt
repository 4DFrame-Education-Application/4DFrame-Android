package com.blockmaker.Blockmaker.data.api

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface BuildCameraRetrofitInterface {
    /** 카메라 페이지: 이미지 보내기 **/

    @Multipart
    @POST("upload")
    suspend fun setImageupload(
        @Part images: List<MultipartBody.Part>
    ): Response<Void>
}