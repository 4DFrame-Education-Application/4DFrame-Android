package com.blockmaker.fdland.data.api

import com.blockmaker.fdland.data.model.ConstructHistory
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ConstHistoryRetrofitInterface {

    @GET("/api/composition/get-list")
    fun getCompositionPlayResultList(
        @Header("X-AUTH-TOKEN") token: String
    ): Call<List<ConstructHistory>>
}