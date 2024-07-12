package com.blockmaker.fdland.presentation.build.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.blockmaker.fdland.databinding.FragmentConstLoadingBinding

class ConstLoadingView : AppCompatActivity() {
    private lateinit var binding: FragmentConstLoadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentConstLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 로딩 애니메이션과 프로그레스 바 활성화
        binding.homeAni.visibility = View.VISIBLE
        binding.progressBar.visibility = View.VISIBLE

        // 일정 시간 지연 이후 실행하기 위한 코드
        Handler(Looper.getMainLooper()).postDelayed({
            // 로딩 애니메이션 종료
            binding.homeAni.visibility = View.GONE
            binding.progressBar.visibility = View.GONE

            // 일정 시간이 지나면 결과 화면으로 이동
            val intent = Intent(this, ConstResultActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000) // 로딩 시간: 3초
    }
}
