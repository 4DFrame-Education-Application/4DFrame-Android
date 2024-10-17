package com.blockmaker.fdland.presentation.mypage.viewmodel

import MyPageRetrofitInterface
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blockmaker.fdland.data.api.RetrofitClient
import com.blockmaker.fdland.data.model.MyPageResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageViewModel : ViewModel() {

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> get() = _userName

    private val _schoolName = MutableLiveData<String>()
    val schoolName: LiveData<String> get() = _schoolName

    private val myPageApi: MyPageRetrofitInterface =
        RetrofitClient.getRetrofit().create(MyPageRetrofitInterface::class.java)

    fun loadUserData(token: String) {
        myPageApi.getMyPageInfo(token).enqueue(object : Callback<MyPageResponse> {
            override fun onResponse(call: Call<MyPageResponse>, response: Response<MyPageResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { data ->
                        _userName.value = data.userName
                        _schoolName.value = data.schoolName
                    }
                }
            }

            override fun onFailure(call: Call<MyPageResponse>, t: Throwable) {
                // 실패 시 처리 로직 (예: 로그 출력)
            }
        })
    }
}