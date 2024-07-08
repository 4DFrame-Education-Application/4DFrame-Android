package com.blockmaker.Blockmaker.data.model

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("name") val name: String,
    @SerializedName("accuracy") val accuracy: String,
    @SerializedName("rate") val rate: String,
    @SerializedName("imageUrl") val imageUrl: String
)