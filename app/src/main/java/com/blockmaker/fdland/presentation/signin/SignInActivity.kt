package com.blockmaker.fdland.presentation.signin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
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
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

class SignInActivity : AppCompatActivity(), SignInView {

    private lateinit var binding: ActivitySignInBinding
    private val authService = AuthService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("auth_prefs", MODE_PRIVATE)
        val isAutoLogin = sharedPreferences.getBoolean("AUTO_LOGIN", false)
        val token = sharedPreferences.getString("X-AUTH-TOKEN", null)

        if (isAutoLogin && token != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        authService.setSignInView(this)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (validateInput(email, password)) {
                authService.signIn(email, password)
            }
        }

        binding.cbAutoLogin.setOnCheckedChangeListener { _, isChecked ->
            binding.tvAutoLoginWarning.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this, EmailVerificationActivity::class.java))
        }

        binding.btnClose.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        binding.btnKakaoLogin.setOnClickListener {
            handleKakaoLogin()
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

    private fun handleKakaoLogin() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("SignInActivity", "카카오계정 로그인 실패", error)
                Toast.makeText(this, "카카오 로그인 실패", Toast.LENGTH_SHORT).show()
            } else if (token != null) {
                Log.i("SignInActivity", "카카오계정 로그인 성공: ${token.accessToken}")
                authService.signInWithKakao(token.accessToken)
            }
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    Log.e("SignInActivity", "카카오톡 로그인 실패", error)

                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡 실패 시 계정 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                } else if (token != null) {
                    Log.i("SignInActivity", "카카오톡 로그인 성공: ${token.accessToken}")
                    authService.signInWithKakao(token.accessToken)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }

    override fun onSignInSuccess(token: String) {
        val isAutoLogin = binding.cbAutoLogin.isChecked
        saveToken(token)
        saveAutoLoginState(isAutoLogin)

        binding.tvLoginFailMessage.visibility = View.GONE
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onSignInFailure() {
        binding.tvLoginFailMessage.text = "아이디 또는 비밀번호가 올바르지 않습니다."
        binding.tvLoginFailMessage.visibility = View.VISIBLE
    }

    private fun saveToken(token: String) {
        val prefs = getSharedPreferences("auth_prefs", MODE_PRIVATE)
        prefs.edit().putString("X-AUTH-TOKEN", token).apply()
    }

    private fun saveAutoLoginState(enabled: Boolean) {
        val prefs = getSharedPreferences("auth_prefs", MODE_PRIVATE)
        prefs.edit().putBoolean("AUTO_LOGIN", enabled).apply()
    }
}