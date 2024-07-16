package com.blockmaker.fdland.presentation.practice.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.blockmaker.fdland.R

class PracticeSecondLoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_prac_loading)

        // 일정 시간 지연 이후 실행하기 위한 코드
        Handler(Looper.getMainLooper()).postDelayed({

            // 일정 시간이 지나면 PracticeSecondResultActivity 이동
            val intent= Intent( this, PracticeSecondResultActivity::class.java)
            startActivity(intent)

            finish()

        }, 5000)
    }
}