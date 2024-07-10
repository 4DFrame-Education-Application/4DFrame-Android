package com.blockmaker.fdland.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://localhost:8080/"
    private var retrofit: Retrofit? = null

    fun getInstance(): Retrofit? {
        if (com.blockmaker.fdland.data.api.RetrofitClient.retrofit == null) {
            val logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            com.blockmaker.fdland.data.api.RetrofitClient.retrofit = Retrofit.Builder()
                .baseUrl(com.blockmaker.fdland.data.api.RetrofitClient.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return com.blockmaker.fdland.data.api.RetrofitClient.retrofit
    }
}