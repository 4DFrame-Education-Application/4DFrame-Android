package com.blockmaker.fdland.presentation.build.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.blockmaker.fdland.databinding.ActivityConstResultBinding
import com.blockmaker.fdland.presentation.main.MainActivity

class ConstResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConstResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConstResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 블럭 쌓기 버튼 클릭 이벤트 처리
        binding.toolbarBtnAgain.setOnClickListener {
            val intent = Intent(this, ConstructActivity::class.java)
            startActivity(intent)
        }

        // 함수호출
        binding.toolbarBtnMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}