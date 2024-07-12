package com.blockmaker.fdland.data.model

import com.google.gson.annotations.SerializedName

data class ConstructResponse(
    @SerializedName("data")
    val constructPosts: List<ConstructImg>
)

data class ConstructImg (
    @SerializedName("imageUrl")
    val imageUrl: String
)

data class ConstructImgRequest(
    @SerializedName("data")
    val constructPosts: List<ConstructImg>
)
