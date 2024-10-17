package com.blockmaker.fdland.data.model

import com.google.gson.annotations.SerializedName

data class MyPageResponse(
    @SerializedName("schoolName") val schoolName: String,
    @SerializedName("userId") val userId: Int,
    @SerializedName("userName") val userName: String
)
