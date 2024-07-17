package com.blockmaker.fdland.data.source.remote.result

import com.blockmaker.fdland.data.api.ResultRetrofitInterface
import com.blockmaker.fdland.data.api.RetrofitClient
import com.blockmaker.fdland.data.model.ResultResponse
import retrofit2.Call

class ResultDataSourceImpl {

    private val resultService =
        RetrofitClient.getRetrofit()?.create(ResultRetrofitInterface::class.java)

    suspend fun getResult(
        name: String?,
        accuracy: String?,
        rate: String?,
        imageUrl: String?
    ): Call<ResultResponse> {
        return resultService!!.getResult(name, accuracy, rate, imageUrl)
    }
}
