package com.blockmaker.Blockmaker.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blockmaker.Blockmaker.databinding.ActivityMainBinding
import com.blockmaker.Blockmaker.presentation.build.view.BuildActivity
import com.blockmaker.Blockmaker.presentation.build.view.ConstructActivity

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
    }
}