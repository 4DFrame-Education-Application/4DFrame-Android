package com.blockmaker.fdland.presentation.mypage.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.blockmaker.fdland.databinding.ActivityMyPageBinding
import com.blockmaker.fdland.presentation.MyPageResultMainActivity
import com.blockmaker.fdland.presentation.mypage.viewmodel.MyPageViewModel
import com.blockmaker.fdland.presentation.signin.SignInActivity

class MyPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyPageBinding
    private val viewModel: MyPageViewModel by viewModels()

    private fun getToken(): String {
        val sharedPreferences = getSharedPreferences("auth_prefs", MODE_PRIVATE)
        val token = sharedPreferences.getString("X-AUTH-TOKEN", null) ?: ""
        Log.d("MyPageActivity", "가져온 토큰: $token")
        return token
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val token = getToken()
        viewModel.loadUserData(token)

        // X 버튼 클릭 시 이전 페이지로 이동
        binding.btnClose.setOnClickListener {
            finish() // 현재 페이지를 닫고 이전 페이지로 돌아감
        }

        binding.btnCheckLoginInfo.setOnClickListener {
            val intent = Intent(this, MyPageConfirmActivity::class.java) // '내 작품 확인' 버튼을 누르면 이동할 페이지 설정
            startActivity(intent)
        }

        binding.btnCheckMyWork.setOnClickListener {
            val intent = Intent(this, MyPageResultMainActivity::class.java) // '내 작품 확인' 버튼을 누르면 이동할 페이지 설정
            startActivity(intent)
        }

        binding.btnMoreAbout4DFrame.setOnClickListener {
            // Intent를 사용해 웹 브라우저 열기
            val url = "http://m.4dframe.com/" // 이동할 URL 설정
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
            }
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener {
            // SharedPreferences에서 토큰 삭제
            val sharedPreferences = getSharedPreferences("auth_prefs", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.remove("X-AUTH-TOKEN")
            editor.apply()

            // 사용자를 로그인 화면으로 리디렉션
            val intent = Intent(this, SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // 모든 Activity 스택을 제거
            startActivity(intent)
            finish()
        }

        viewModel.userName.observe(this, Observer { userName ->
            binding.tvUserName.text = userName
        })

        viewModel.schoolName.observe(this, Observer { schoolName ->
            binding.tvUserSchool.text = schoolName
        })
    }
}