package com.blockmaker.fdland.data.model

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("msg") val msg: String,
    @SerializedName("detailMessage") val detailMessage: String,
    @SerializedName("token") val token: String  // JWT 토큰 필드
)


data class SendEmailResponse(
    @SerializedName("Confirmation : ")
    val confirmation: String?
)

data class SendVerifiedResponse(
    @SerializedName("Verified") val Verified: Boolean
)

data class SignUpResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("msg") val msg: String,
    @SerializedName("detailMessage") val detailMessage: String,
)