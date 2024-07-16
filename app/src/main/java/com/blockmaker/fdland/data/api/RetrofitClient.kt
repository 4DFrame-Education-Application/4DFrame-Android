////package com.greenfriends.zeroway.data.api
//package com.blockmaker.fdland.data.api
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import java.util.concurrent.TimeUnit
//
//object RetrofitClient {
//    private const val BASE_URL = "http://52.79.163.1:8080/"
//    private var retrofit: Retrofit? = null
//
//    fun getRetrofit(): Retrofit? {
//        if (retrofit == null) {
//            val logger = HttpLoggingInterceptor().apply {
//                level = HttpLoggingInterceptor.Level.BASIC
//            }
//            val client = OkHttpClient.Builder()
//                .addInterceptor(logger)
//                .connectTimeout(300, TimeUnit.MINUTES)
//                .readTimeout(300,TimeUnit.MINUTES)
//                .writeTimeout(300,TimeUnit.MINUTES)
//                .build()
//
//            retrofit = Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .client(client)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//        }
//        return retrofit
//    }
//}
package com.blockmaker.fdland.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    // Retrofit의 기본 URL 설정
    private const val BASE_URL = "http://52.79.163.1:8080/"

    // Retrofit 인스턴스를 지연 초기화(lazy initialization) 방식으로 초기화
    private val retrofitInstance: Retrofit by lazy {
        // HTTP 로깅 인터셉터 설정 (BASIC 수준)
        val logger = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        // OkHttp 클라이언트 설정 (타임아웃과 로깅 인터셉터 포함)
        val client = OkHttpClient.Builder()
            .addInterceptor(logger) // 로깅 인터셉터 추가
            .connectTimeout(300, TimeUnit.SECONDS) // 연결 타임아웃 설정
            .readTimeout(300, TimeUnit.SECONDS) // 읽기 타임아웃 설정
            .writeTimeout(300, TimeUnit.SECONDS) // 쓰기 타임아웃 설정
            .build()

        // Retrofit 빌더를 사용하여 Retrofit 인스턴스 생성
        Retrofit.Builder()
            .baseUrl(BASE_URL) // 기본 URL 설정
            .client(client) // OkHttp 클라이언트 설정
            .addConverterFactory(GsonConverterFactory.create()) // Gson 변환기 추가
            .build() // Retrofit 인스턴스 빌드
    }

    // Retrofit 인스턴스를 반환하는 함수
    fun getRetrofit(): Retrofit = retrofitInstance
}
