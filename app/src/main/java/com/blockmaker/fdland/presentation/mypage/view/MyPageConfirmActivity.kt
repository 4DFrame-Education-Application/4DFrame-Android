package com.blockmaker.fdland.presentation.mypage.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.blockmaker.fdland.databinding.ActivityMyPageConfirmBinding
import com.blockmaker.fdland.presentation.mypage.viewmodel.MyPageViewModel

class MyPageConfirmActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyPageConfirmBinding
    private val viewModel: MyPageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyPageConfirmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        binding.toolbarPrevious.setOnClickListener {
            val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnEditInfo.setOnClickListener {
            val intent = Intent(this, MyPageModifyActivity::class.java) // Replace with your actual activity
            startActivity(intent)
            finish()
        }

        val token = getToken()
        viewModel.loadUserData(token)

        viewModel.userName.observe(this) { userName ->
            binding.tvUserName.text = "$userName 님"
        }

        viewModel.schoolName.observe(this) { schoolName ->
            binding.tvUserSchool.text = schoolName
        }
    }

    private fun getToken(): String {
        val sharedPreferences = getSharedPreferences("auth_prefs", MODE_PRIVATE)
        return sharedPreferences.getString("X-AUTH-TOKEN", "") ?: ""
    }
}