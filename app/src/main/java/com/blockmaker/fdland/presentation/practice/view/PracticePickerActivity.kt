package com.blockmaker.fdland.presentation.practice.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.blockmaker.fdland.R
import com.blockmaker.fdland.presentation.main.HomeActivity

class PracticePickerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_prac_picker)

        val imageView1: ImageView = findViewById(R.id.imageView1)
        val imageView2: ImageView = findViewById(R.id.imageView2)
        val imageView3: ImageView = findViewById(R.id.imageView3)
        val imageView4: ImageView = findViewById(R.id.imageView4)
        val imageView5: ImageView = findViewById(R.id.imageView5)

        val button1: Button = findViewById(R.id.toolbar_previous)
        val button2: Button = findViewById(R.id.toolbar_type)

        // 이전 버튼
        button1.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        // 유형 보기 ((추후 업뎃))
        button2.setOnClickListener {
            val intent = Intent(this, PracticeTypeExplainActivity::class.java)
            startActivity(intent)
        }

        // 결과창 1번
        imageView1.setOnClickListener {
            val intent = Intent(this, PracticeFirstLoadingActivity::class.java)
            startActivity(intent)
        }

        // 결과창 2번
        imageView2.setOnClickListener {
            val intent = Intent(this, PracticeSecondLoadingActivity::class.java)
            startActivity(intent)
        }

        // 결과창 3번
        imageView3.setOnClickListener {
            val intent = Intent(this, PracticeThirdLoadingActivity::class.java)
            startActivity(intent)
        }

        // 결과창 4번
        imageView4.setOnClickListener {
            val intent = Intent(this, PracticeFourthLoadingActivity::class.java)
            startActivity(intent)
        }

        // 결과창 5번
        imageView5.setOnClickListener {
            val intent = Intent(this, PracticeFifthLoadingActivity::class.java)
            startActivity(intent)
        }
    }
}
