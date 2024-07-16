package com.blockmaker.fdland.presentation.build.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
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

        // Intent로부터 데이터를 받아옴
        val imageUrl = intent.getStringExtra("image_url")
        Log.d("ConstLoadingView", "Received Image URL: $imageUrl")

        // 일정 시간 지연 이후 실행하기 위한 코드
        Handler(Looper.getMainLooper()).postDelayed({
            // 로딩 애니메이션 종료
            binding.homeAni.visibility = View.GONE
            binding.progressBar.visibility = View.GONE

            // 일정 시간이 지나면 결과 화면으로 이동
            val intent = Intent(this, ConstResultActivity::class.java).apply {
                putExtra("image_url", imageUrl) // image_url을 Intent에 추가
            }
            startActivity(intent)
            finish()
        }, 3000) // 로딩 시간: 3초
    }
}
