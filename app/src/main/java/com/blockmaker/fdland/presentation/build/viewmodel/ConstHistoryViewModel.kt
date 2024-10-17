package com.blockmaker.fdland.presentation.build.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blockmaker.fdland.data.api.ConstHistoryRetrofitInterface
import com.blockmaker.fdland.data.model.ConstructHistory
import com.blockmaker.fdland.data.api.RetrofitClient
import com.blockmaker.fdland.presentation.build.adapter.ConstHistoryAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConstHistoryViewModel : ViewModel() {

    private val _constHistoryList = MutableLiveData<List<ConstructHistory>>()
    val constHistoryList: LiveData<List<ConstructHistory>> get() = _constHistoryList

    val constHistoryAdapter = ConstHistoryAdapter()

    fun loadConstHistoryList(token: String) {
        val api = RetrofitClient.getRetrofit().create(ConstHistoryRetrofitInterface::class.java)

        api.getCompositionPlayResultList(token).enqueue(object : Callback<List<ConstructHistory>> {
            override fun onResponse(
                call: Call<List<ConstructHistory>>,
                response: Response<List<ConstructHistory>>
            ) {
                if (response.isSuccessful) {
                    _constHistoryList.value = response.body()
                    constHistoryAdapter.submitList(response.body())
                }
            }

            override fun onFailure(call: Call<List<ConstructHistory>>, t: Throwable) {
                // 실패 시 처리
            }
        })
    }
}