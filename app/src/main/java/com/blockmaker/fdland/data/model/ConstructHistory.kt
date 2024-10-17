package com.blockmaker.fdland.data.model

import com.google.gson.annotations.SerializedName

data class ConstructHistory(
    @SerializedName("createDate") val createDate: String,
    @SerializedName("id") val id: Int,
    @SerializedName("imageUrl") val imageUrl: String
)