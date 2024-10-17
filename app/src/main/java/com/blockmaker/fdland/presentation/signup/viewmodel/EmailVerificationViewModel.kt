package com.blockmaker.fdland.presentation.signup.viewmodel

import AuthRetrofitInterface
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blockmaker.fdland.data.api.RetrofitClient
import com.blockmaker.fdland.data.model.SendEmailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmailVerificationViewModel : ViewModel() {

    fun sendVerificationEmail(email: String): LiveData<SendEmailResponse?> {
        val responseLiveData = MutableLiveData<SendEmailResponse?>()

        val emailService = RetrofitClient.getRetrofit().create(AuthRetrofitInterface::class.java)
        val call = emailService.sendEmail(email)

        call.enqueue(object : Callback<SendEmailResponse> {
            override fun onResponse(call: Call<SendEmailResponse>, response: Response<SendEmailResponse>) {
                if (response.isSuccessful) {
                    responseLiveData.postValue(response.body())
                } else {
                    responseLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<SendEmailResponse>, t: Throwable) {
                responseLiveData.postValue(null)
            }
        })

        return responseLiveData
    }
}