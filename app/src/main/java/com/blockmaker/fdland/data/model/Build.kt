package com.blockmaker.fdland.data.model

import com.google.gson.annotations.SerializedName

data class BuildResult(
    @SerializedName("accuracy")
    val accuracy: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("rate")
    val rate: String
)

data class BuildResultResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("results")
    val results: List<BuildResult>,
    @SerializedName("back_image")
    val back_image: String,
    @SerializedName("front_image")
    val front_image: String,
    @SerializedName("left_image")
    val left_image: String,
    @SerializedName("right_image")
    val right_image: String,
    @SerializedName("up_image")
    val up_image: String
)
