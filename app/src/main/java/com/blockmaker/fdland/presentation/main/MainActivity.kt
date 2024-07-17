package com.blockmaker.fdland.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blockmaker.fdland.databinding.ActivityMainBinding
import com.blockmaker.fdland.presentation.build.view.BuildActivity
import com.blockmaker.fdland.presentation.build.view.ConstructActivity
import com.blockmaker.fdland.presentation.home.HomeActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 블럭 쌓기 버튼 클릭 이벤트 처리
        binding.buttonLinearLayout1.setOnClickListener {
            val intent = Intent(this, BuildActivity::class.java)
            startActivity(intent)
        }

        // 구성 놀이 버튼 클릭 이벤트 처리
        binding.buttonLinearLayout2.setOnClickListener {
            val intent = Intent(this, ConstructActivity::class.java)
            startActivity(intent)
        }

        // Toolbar previous button 클릭 이벤트 처리
        binding.toolbarPrevious.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}
