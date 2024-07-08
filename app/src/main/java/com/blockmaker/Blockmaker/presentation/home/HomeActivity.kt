package com.blockmaker.Blockmaker.presentation.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.blockmaker.Blockmaker.R

class HomeActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val button1: Button = findViewById(R.id.home_button1)
        val button2: Button = findViewById(R.id.home_button2)

        // 블럭 쌓기 버튼 클릭 이벤트 처리
        button1.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // 구성 놀이 버튼 클릭 이벤트 처리
        button2.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
