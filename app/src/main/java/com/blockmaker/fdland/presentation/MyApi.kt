package com.blockmaker.fdland.presentation

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface MyApi {
    @GET("upload/result")
    fun getPost1(): Call<POST>
}