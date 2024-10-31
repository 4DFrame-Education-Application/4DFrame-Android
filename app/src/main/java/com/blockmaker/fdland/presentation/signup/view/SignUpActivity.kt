package com.blockmaker.fdland.presentation.signup.view

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.blockmaker.fdland.R
import com.blockmaker.fdland.presentation.signin.SignInActivity

class SignUpActivity : AppCompatActivity() {

    private lateinit var spinnerUserGrade: Spinner
    private lateinit var btnCompleteSignUp: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join) // 레이아웃 파일을 사용합니다.

        // XML에서 정의된 뷰를 초기화합니다.
        spinnerUserGrade = findViewById(R.id.spinnerAgeGroup)
        btnCompleteSignUp = findViewById(R.id.btnCompleteSignUp)

        // 스피너에 데이터를 설정합니다.
        val ageGroups = resources.getStringArray(R.array.spinner_age).toList()
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, ageGroups)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerUserGrade.adapter = spinnerAdapter

        // 버튼 클릭 리스너 설정
        btnCompleteSignUp.setOnClickListener {
            // SignInActivity로 이동
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish() // 현재 액티비티를 종료하여 뒤로가기로 돌아오지 않도록 함
        }
    }
}