package com.blockmaker.fdland.data.source.remote.result

import com.blockmaker.fdland.data.api.ResultRetrofitInterface
import com.blockmaker.fdland.data.model.ResultResponse
//import com.greenfriends.zeroway.data.api.RetrofitClient
import com.blockmaker.fdland.data.api.ConstructRetrofitInterface
import com.blockmaker.fdland.data.api.RetrofitClient
import retrofit2.Call
import retrofit2.Response

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
