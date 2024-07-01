package com.blockmaker.Blockmaker.util.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.blockmaker.Blockmaker.R
import com.blockmaker.Blockmaker.util.build.BuildActivity
import com.blockmaker.Blockmaker.util.construct.ConstructActivity

class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 블럭 쌓기 버튼 클릭 이벤트 처리
        val buttonLinearLayout1: LinearLayout = findViewById(R.id.buttonLinearLayout1)
        buttonLinearLayout1.setOnClickListener {
            val intent = Intent(this, BuildActivity::class.java)
            startActivity(intent)
        }

        // 구성 놀이 버튼 클릭 이벤트 처리
        val buttonLinearLayout2: LinearLayout = findViewById(R.id.buttonLinearLayout2)
        buttonLinearLayout2.setOnClickListener {
            val intent = Intent(this, ConstructActivity::class.java)
            startActivity(intent)
        }
    }
}