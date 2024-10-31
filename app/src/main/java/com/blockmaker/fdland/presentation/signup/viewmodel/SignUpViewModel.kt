package com.blockmaker.fdland.presentation.signup.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blockmaker.fdland.data.api.AuthRetrofitInterface
import com.blockmaker.fdland.data.api.RetrofitClient
import com.blockmaker.fdland.data.model.SignUpResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel : ViewModel() {

    private val authService = RetrofitClient.getRetrofit().create(AuthRetrofitInterface::class.java)
    private val _signUpResult = MutableLiveData<SignUpResponse?>()
    val signUpResult: LiveData<SignUpResponse?> get() = _signUpResult

    // 회원가입 요청을 서버에 보냅니다.
    fun signUp(
        name: String,
        password: String,
        birthDate: String,
        phoneNumber: String,
        userGrade: String
    ) {
        val call = authService.signUp(
            userName = name,
            password = password,
            birth = birthDate,
            phoneNumber = phoneNumber,
            school = "N/A", // 필요에 따라 수정
            schoolName = "N/A", // 필요에 따라 수정
            userGrade = userGrade
        )

        // 서버의 응답을 처리합니다.
        call.enqueue(object : Callback<SignUpResponse> {
            override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                if (response.isSuccessful) {
                    _signUpResult.value = response.body() // 성공 시 결과 업데이트
                } else {
                    _signUpResult.value = SignUpResponse(
                        success = false,
                        code = 400,
                        msg = "회원가입 실패",
                        detailMessage = response.message() // detailMessage에 응답 메시지를 전달합니다.
                    )
                }
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                _signUpResult.value = SignUpResponse(
                    success = false,
                    code = 500,
                    msg = "네트워크 오류",
                    detailMessage = t.message ?: "알 수 없는 오류 발생" // detailMessage에 실패 원인을 전달합니다.
                )
            }
        })
    }
}