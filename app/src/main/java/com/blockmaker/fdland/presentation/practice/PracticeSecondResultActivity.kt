package com.blockmaker.fdland.presentation.practice

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.blockmaker.fdland.R
import com.blockmaker.fdland.presentation.main.HomeActivity

class PracticeSecondResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_prac_page2)

        val button1: Button = findViewById(R.id.toolbar_btn_again)
        val button2: Button = findViewById(R.id.toolbar_btn_main)

        // 이전 버튼
        button1.setOnClickListener {
            val intent = Intent(this, PracticePickerActivity::class.java)
            startActivity(intent)
        }

        // 유형 보기 ((추후 업뎃))
        button2.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }}