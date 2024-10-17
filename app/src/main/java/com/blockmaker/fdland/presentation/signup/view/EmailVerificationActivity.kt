package com.blockmaker.fdland.presentation.signup.view

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.blockmaker.fdland.R
import com.blockmaker.fdland.presentation.signup.viewmodel.EmailVerificationViewModel

class EmailVerificationActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etVerificationCode: EditText
    private lateinit var btnVerifyEmail: Button
    private lateinit var btnCheckCode: Button
    private lateinit var btnJoinNext: Button
    private lateinit var viewModel: EmailVerificationViewModel

    private var receivedConfirmationCode: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_veritify)

        etEmail = findViewById(R.id.etEmail)
        etVerificationCode = findViewById(R.id.etVerificationCode)
        btnVerifyEmail = findViewById(R.id.btnVerifyEmail)
        btnCheckCode = findViewById(R.id.btnCheckCode)
        btnJoinNext = findViewById(R.id.btnJoinNext)

        viewModel = ViewModelProvider(this)[EmailVerificationViewModel::class.java]

        // 초기 버튼 상태 설정
        btnCheckCode.isEnabled = false  // 인증번호 확인 버튼 비활성화
        btnJoinNext.isEnabled = false    // 다음 버튼 비활성화

        // 인증번호 전송 버튼 클릭 시
        btnVerifyEmail.setOnClickListener {
            val email = etEmail.text.toString()
            if (email.isNotEmpty()) {
                sendVerificationEmail(email)

                // 버튼 비활성화 (3분 동안)
                btnVerifyEmail.isEnabled = false
                startCountDownTimer(180000) // 3분 타이머 시작

                // 인증 확인 버튼 활성화
                btnCheckCode.isEnabled = true
            } else {
                Log.e("EmailVerifyAct", "이메일을 입력하세요.")
            }
        }

        // 인증 확인 버튼 클릭 시
        btnCheckCode.setOnClickListener {
            verifyCode()
        }

        // 다음 버튼 클릭 시 SignUpActivity로 이동
        btnJoinNext.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()  // 현재 Activity 종료
        }
    }

    private fun startCountDownTimer(timeInMillis: Long) {
        object : CountDownTimer(timeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                btnVerifyEmail.text = "${millisUntilFinished / 1000}초 후 다시 전송"
            }

            override fun onFinish() {
                btnVerifyEmail.isEnabled = true
                btnVerifyEmail.text = "인증번호 전송"
            }
        }.start()
    }

    // 인증번호 전송
    private fun sendVerificationEmail(email: String) {
        viewModel.sendVerificationEmail(email).observe(this) { response ->
            if (response != null) {
                receivedConfirmationCode = response.confirmation
                Log.d("EmailVerifyAct", "이메일 전송 성공, 확인 코드: $receivedConfirmationCode")
                Toast.makeText(this, "인증번호가 전송되었습니다.", Toast.LENGTH_SHORT).show()
            } else {
                Log.e("EmailVerifyAct", "이메일 전송 실패")
                Toast.makeText(this, "인증번호 전송에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 인증 코드 확인 로직
    private fun verifyCode() {
        val enteredCode = etVerificationCode.text.toString()

        if (enteredCode == receivedConfirmationCode) {
            Log.d("EmailVerifyAct", "인증 성공!")
            Toast.makeText(this, "인증 성공!", Toast.LENGTH_SHORT).show()

            // 인증 성공 시 다음 버튼 활성화
            btnJoinNext.isEnabled = true
        } else {
            Log.e("EmailVerifyAct", "인증 번호가 일치하지 않습니다.")
            Toast.makeText(this, "인증 번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}