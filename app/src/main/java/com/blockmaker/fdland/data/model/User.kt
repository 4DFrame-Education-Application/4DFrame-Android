package com.blockmaker.fdland.data.model

import com.google.gson.annotations.SerializedName

data class SignInRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

data class AuthResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("msg") val msg: String,
    @SerializedName("detailMessage") val detailMessage: String,
    @SerializedName("token") val token: String  // JWT 토큰 필드
)