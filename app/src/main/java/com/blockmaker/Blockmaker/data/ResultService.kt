package com.blockmaker.Blockmaker.data

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ResultService {
    @Multipart
    @POST("results/upload")
    fun uploadImage(
        @Part image: MultipartBody.Part
    ): Call<ImageResponse>
}

interface ImageResponse {

}

data class ResultResponse(
    val name: String,
    val accuracy: String,
    val rate: String,
    val imageUrl: String
)
