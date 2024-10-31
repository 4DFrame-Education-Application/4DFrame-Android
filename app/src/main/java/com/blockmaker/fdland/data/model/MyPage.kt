package com.blockmaker.fdland.data.model

import com.google.gson.annotations.SerializedName

data class MyPageResponse(
    @SerializedName("schoolName") val schoolName: String,
    @SerializedName("userId") val userId: Int,
    @SerializedName("userName") val userName: String
)

data class MyPageConfirmResponse(
    @SerializedName("schoolName") val schoolName: String,
    @SerializedName("userId") val userId: Int,
    @SerializedName("userName") val userName: String,
    @SerializedName("email") val email: String,
    @SerializedName("birth") val birth: String,
    @SerializedName("phoneNumber") val phoneNumber: String
)
