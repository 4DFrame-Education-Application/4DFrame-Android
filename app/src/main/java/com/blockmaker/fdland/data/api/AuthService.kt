package com.blockmaker.fdland.data.api

import AuthRetrofitInterface
import android.util.Log
import com.blockmaker.fdland.data.model.AuthResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {

    private var retrofit = RetrofitClient.getRetrofit()
    private lateinit var signInView: SignInView

    fun setSignInView(signInView: SignInView) {
        this.signInView = signInView
    }

    fun signIn(email: String, password: String) {
        val authService = retrofit.create(AuthRetrofitInterface::class.java)
        authService.login(email, password).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    // JWT 토큰 받기
                    val token = response.body()!!.token
                    signInView.onSignInSuccess(token)
                } else {
                    Log.d("API/LOGIN/FAILURE", "Response is unsuccessful")
                    signInView.onSignInFailure()
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("API/LOGIN/FAILURE", "Request failed: ${t.message}")
                signInView.onSignInFailure()
            }
        })
    }
}