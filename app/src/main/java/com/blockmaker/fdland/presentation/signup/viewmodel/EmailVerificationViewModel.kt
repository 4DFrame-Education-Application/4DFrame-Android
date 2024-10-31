package com.blockmaker.fdland.presentation.signup.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blockmaker.fdland.data.api.AuthRetrofitInterface
import com.blockmaker.fdland.data.api.RetrofitClient
import com.blockmaker.fdland.data.model.SendEmailResponse
import com.blockmaker.fdland.data.model.SendVerifiedResponse
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
                if (response.isSuccessful && response.body()?.confirmation != null) {
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

    fun verifyEmailCode(confirmationCode: String): LiveData<SendVerifiedResponse?> {
        val responseLiveData = MutableLiveData<SendVerifiedResponse?>()
        val emailService = RetrofitClient.getRetrofit().create(AuthRetrofitInterface::class.java)
        val call = emailService.sendVerified(confirmationCode)

        call.enqueue(object : Callback<SendVerifiedResponse> {
            override fun onResponse(call: Call<SendVerifiedResponse>, response: Response<SendVerifiedResponse>) {
                if (response.isSuccessful && response.body()?.Verified == true) {
                    responseLiveData.postValue(response.body())
                } else {
                    responseLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<SendVerifiedResponse>, t: Throwable) {
                responseLiveData.postValue(null)
            }
        })

        return responseLiveData
    }
}