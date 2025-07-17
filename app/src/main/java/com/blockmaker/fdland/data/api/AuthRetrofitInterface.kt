package com.blockmaker.fdland.data.api

import com.blockmaker.fdland.data.model.AuthResponse
import com.blockmaker.fdland.data.model.SendEmailResponse
import com.blockmaker.fdland.data.model.SendVerifiedResponse
import com.blockmaker.fdland.data.model.SignUpResponse
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthRetrofitInterface {
    @POST("api/sign/sign-in")
    fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): Call<AuthResponse>

    @POST("/api/sign/send-mail")
    fun sendEmail(
        @Query("email") email: String
    ): Call<SendEmailResponse>

    @POST("/api/sign/verified")
    fun sendVerified(
        @Query("confirmationCode") confirmationCode: String
    ): Call<SendVerifiedResponse>

    @POST("/api/sign/sign-up")
    fun signUp(
        @Query("birth") birth: String,
        @Query("password") password: String,
        @Query("phoneNumber") phoneNumber: String,
        @Query("school") school: String,
        @Query("schoolName") schoolName: String,
        @Query("userGrade") userGrade: String,
        @Query("userName") userName: String
    ): Call<SignUpResponse>

    @POST("/api/sign/kakao-login")
    fun loginWithKakao(
        @retrofit2.http.Header("Authorization") kakaoAccessToken: String
    ): Call<AuthResponse>
}