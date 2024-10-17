package com.blockmaker.fdland.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.blockmaker.fdland.R

class BuildListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_build_list_result)

        val buttonAgain = findViewById<Button>(R.id.toolbar_btn_again)
        val buttonMain = findViewById<Button>(R.id.toolbar_btn_main)

        buttonAgain.setOnClickListener {
            val intent = Intent(this, MyPageResulttoBuildActivity::class.java)
            startActivity(intent)
        }

        buttonMain.setOnClickListener {
            val intent = Intent(this, MyPageResultMainActivity::class.java)
            startActivity(intent)
        }
    }
}