package com.blockmaker.fdland.data.model

import com.google.gson.annotations.SerializedName

data class BuildResult(
    @SerializedName("accuracy") val accuracy: String,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("name") val name: String,
    @SerializedName("rate") val rate: String
)

data class BuildResultResponse(
    @SerializedName("data") val BuildResultResponse: List<BuildResult>
)
