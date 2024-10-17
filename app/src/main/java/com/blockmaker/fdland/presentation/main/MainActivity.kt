package com.blockmaker.fdland.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blockmaker.fdland.databinding.ActivityMainBinding
import com.blockmaker.fdland.presentation.build.view.BuildActivity
import com.blockmaker.fdland.presentation.build.view.ConstructActivity
import com.blockmaker.fdland.presentation.home.HomeActivity
import com.blockmaker.fdland.presentation.mypage.view.MyPageActivity

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

        binding.toolbarMyPage.setOnClickListener {
            val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onPause() {
        super.onPause()
        // 액티비티가 일시 정지될 때 수행할 작업
        saveUserData()
    }

    override fun onStop() {
        super.onStop()
        // 액티비티가 중지될 때 수행할 작업
        releaseResources()
    }

    override fun onDestroy() {
        super.onDestroy()
        // 액티비티가 파괴될 때 수행할 작업
        cleanUp()
    }

    private fun saveUserData() {
        // 사용자 데이터를 저장하는 로직
    }

    private fun releaseResources() {
        // 리소스를 해제하는 로직
    }

    private fun cleanUp() {
        // 메모리 누수를 방지하는 정리 작업
    }
}
