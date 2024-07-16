package com.blockmaker.fdland.data.model

import com.google.gson.annotations.SerializedName

data class ConstructImgResponse(
    @SerializedName("imageUrl")
    val imageUrl: String
)

data class ConstructImgRequest(
    @SerializedName("imageUrl")
    val imageUrl: String
)
