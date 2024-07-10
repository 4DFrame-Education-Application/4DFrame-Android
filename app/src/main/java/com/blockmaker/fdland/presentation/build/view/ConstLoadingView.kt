package com.blockmaker.fdland.presentation.build.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.blockmaker.fdland.R

class ConstLoadingView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_const_loading)

        // 일정 시간 지연 이후 실행하기 위한 코드
        Handler(Looper.getMainLooper()).postDelayed({

            // 일정 시간이 지나면 MainActivity로 이동
            val intent= Intent( this, ConstResultActivity::class.java)
            startActivity(intent)

            finish()

        }, 3000) // 시간 0.5초 이후 실행
    }
}