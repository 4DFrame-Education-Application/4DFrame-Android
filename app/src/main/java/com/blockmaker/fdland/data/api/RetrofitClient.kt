package com.blockmaker.fdland.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL = "http://52.79.163.1:8080/"

    private val retrofitInstance: Retrofit by lazy {

        // 로깅 인터셉터 설정
        val logger = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        // OkHttpClient에 타임아웃 설정 추가
        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .connectTimeout(300, TimeUnit.SECONDS)
            .readTimeout(300, TimeUnit.SECONDS)
            .writeTimeout(300, TimeUnit.SECONDS)
            .build()

        // Retrofit 인스턴스 생성
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)  // 타임아웃 설정된 OkHttpClient 사용
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getRetrofit(): Retrofit = retrofitInstance
}