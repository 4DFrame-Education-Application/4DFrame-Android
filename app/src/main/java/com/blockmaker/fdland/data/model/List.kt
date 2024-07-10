package com.blockmaker.fdland.data.model

import com.google.gson.annotations.SerializedName

data class List(
    @SerializedName("name")
    val name: String,
    @SerializedName("accuracy")
    val accuracy: String,
    @SerializedName("imageUrl")
    val imageUrl: String
)
