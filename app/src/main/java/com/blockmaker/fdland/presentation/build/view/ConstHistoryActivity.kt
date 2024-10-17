package com.blockmaker.fdland.presentation.build.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.blockmaker.fdland.databinding.ActivityResultCheckBuildBinding
import com.blockmaker.fdland.presentation.build.viewmodel.ConstHistoryViewModel

class ConstHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultCheckBuildBinding
    private val viewModel: ConstHistoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Data Binding 설정
        binding = ActivityResultCheckBuildBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // RecyclerView 어댑터 설정
        binding.recyclerView.adapter = viewModel.constHistoryAdapter

        // SharedPreferences에서 토큰을 가져와서 사용
        val token = getToken()

        // ViewModel에서 데이터 로드
        viewModel.loadConstHistoryList(token)
    }

    // SharedPreferences에서 토큰을 가져오는 함수
    private fun getToken(): String {
        val sharedPreferences = getSharedPreferences("auth_prefs", MODE_PRIVATE)
        return sharedPreferences.getString("X-AUTH-TOKEN", "") ?: ""  // 토큰이 없을 경우 빈 문자열 반환
    }
}