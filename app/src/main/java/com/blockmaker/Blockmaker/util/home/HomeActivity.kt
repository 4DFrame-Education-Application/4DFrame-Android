package com.blockmaker.Blockmaker.util.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.blockmaker.Blockmaker.util.main.MainActivity
import com.blockmaker.Blockmaker.R
import com.blockmaker.Blockmaker.util.build.BuildActivity
import com.blockmaker.Blockmaker.util.build.BuildResultActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val moveButton = findViewById<Button>(R.id.home_button2)

        // 페이지 이동
        fun moveToAnotherPage(){
            val intent = Intent(this, BuildActivity::class.java)
            startActivity(intent)
        }

        // 함수호출
        moveButton.setOnClickListener{
            moveToAnotherPage()
        }

    }
}