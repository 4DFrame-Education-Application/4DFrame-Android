package com.blockmaker.fdland.data.model

import com.google.gson.annotations.SerializedName

data class ResultList (
    @SerializedName("name")
    val name: String,
    @SerializedName("accuracy")
    val accuracy: String,
    @SerializedName("rate")
    val rate: String,
    @SerializedName("imageUrl")
    val imageUrl: String
    )

data class ResultResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("results")
    val results: List<ResultList>
)