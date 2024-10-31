package com.blockmaker.fdland.presentation

import com.blockmaker.fdland.R

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.LinearLayout
import com.blockmaker.fdland.presentation.mypage.view.MyPageActivity

class MyPageResultMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_check)

        val prevButton = findViewById<Button>(R.id.toolbar_previous)
        val buildButton = findViewById<LinearLayout>(R.id.buttonLinearLayout1)
        val constButton = findViewById<LinearLayout>(R.id.buttonLinearLayout2)

        prevButton.setOnClickListener {
            val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)
            finish()
        }

        buildButton.setOnClickListener {
            val intent = Intent(this, MyPageResulttoBuildActivity::class.java)
            startActivity(intent)
            finish()
        }

        constButton.setOnClickListener {
            val intent = Intent(this, MyPageResulttoConstActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}