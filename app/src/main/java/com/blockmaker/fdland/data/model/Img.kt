package com.blockmaker.fdland.data.model

import com.google.gson.annotations.SerializedName

data class Img(
    @SerializedName(value = "account")
    var id : String,
    @SerializedName(value = "password")
    var password : String
)