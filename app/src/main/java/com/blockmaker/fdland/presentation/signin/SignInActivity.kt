package com.blockmaker.fdland.presentation.signin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.blockmaker.fdland.R
import com.blockmaker.fdland.data.api.AuthService
import com.blockmaker.fdland.data.api.SignInView
import com.blockmaker.fdland.databinding.ActivitySignInBinding
import com.blockmaker.fdland.presentation.home.HomeActivity
import com.blockmaker.fdland.presentation.main.MainActivity
import com.blockmaker.fdland.presentation.signup.view.EmailVerificationActivity

class SignInActivity : AppCompatActivity(), SignInView {

    private lateinit var binding: ActivitySignInBinding
    private val authService = AuthService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        authService.setSignInView(this)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (validateInput(email, password)) {
                authService.signIn(email, password)  // Query 파라미터로 전달
            }
        }

        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this, EmailVerificationActivity::class.java)
            startActivity(intent)
        }

        binding.btnClose.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        return when {
            email.isEmpty() -> {
                binding.etEmail.error = "이메일을 입력해주세요."
                false
            }
            password.isEmpty() -> {
                binding.etPassword.error = "비밀번호를 입력해주세요."
                false
            }
            else -> true
        }
    }

    override fun onSignInSuccess(token: String) {
        Log.d("SignInActivity", "로그인 성공: 사용자 token = $token")
        saveToken(token)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun saveToken(token: String) {
        val sharedPreferences = getSharedPreferences("auth_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("X-AUTH-TOKEN", token) // 토큰을 X-AUTH-TOKEN으로 저장
        editor.apply()
    }

    override fun onSignInFailure() {
        Log.d("SignInActivity", "로그인 실패")
        Toast.makeText(this, "로그인에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
    }
}