package com.blockmaker.fdland.data.repository

import com.blockmaker.fdland.data.source.remote.construct.ConstructDataSource
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response

class ConstRepository(private val constructDataSource: ConstructDataSource) {
    suspend fun setConstImg(token: String, imgUrl: MultipartBody.Part): Response<ResponseBody> {
        return constructDataSource.setConstImg(token, imgUrl)  // Pass the token to the data source
    }
}