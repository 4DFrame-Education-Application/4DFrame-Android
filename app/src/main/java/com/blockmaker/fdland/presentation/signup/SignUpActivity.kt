//package com.blockmaker.fdland.presentation.signup
//
//import android.content.Intent
//import android.os.Bundle
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.databinding.DataBindingUtil
//import com.blockmaker.fdland.R
//import com.blockmaker.fdland.data.api.AuthService
//import com.blockmaker.fdland.data.api.SignUpView
//import com.blockmaker.fdland.presentation.login.SignInActivity
//
//class SignUpActivity : AppCompatActivity(), SignUpView {
//
//}
//
////    private lateinit var binding: ActivitySignUpBinding
////    private val authService = AuthService()
////
////    override fun onCreate(savedInstanceState: Bundle?) {
////        super.onCreate(savedInstanceState)
////        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
////
////        authService.setSignUpView(this)
////
////        binding.btnSignUp.setOnClickListener {
////            val email = binding.etEmail.text.toString().trim()
////            val password = binding.etPassword.text.toString().trim()
////            val confirmPassword = binding.etConfirmPassword.text.toString().trim()
////
////            if (validateInput(email, password, confirmPassword)) {
////                signUp(email, password)
////            }
////        }
////
////        binding.btnClose.setOnClickListener {
////            finish() // 회원가입 화면 종료
////        }
////    }
////
////    private fun validateInput(email: String, password: String, confirmPassword: String): Boolean {
////        return when {
////            email.isEmpty() -> {
////                binding.etEmail.error = "이메일을 입력해주세요."
////                false
////            }
////            password.isEmpty() -> {
////                binding.etPassword.error = "비밀번호를 입력해주세요."
////                false
////            }
////            password != confirmPassword -> {
////                binding.etConfirmPassword.error = "비밀번호가 일치하지 않습니다."
////                false
////            }
////            else -> true
////        }
////    }
////
////    private fun signUp(email: String, password: String) {
////        val signUpRequest = SignUpRequest(email, password)
////        authService.signUp(signUpRequest)
////    }
////
////    override fun onSignUpSuccess() {
////        Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()
////        val intent = Intent(this, SignInActivity::class.java)
////        startActivity(intent)
////        finish()
////    }
////
////    override fun onSignUpFailure() {
////        Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
////    }
////}
