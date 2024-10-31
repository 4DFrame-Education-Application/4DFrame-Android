package com.blockmaker.fdland.presentation.mypage.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.blockmaker.fdland.databinding.ActivityMyPageModifyBinding
import com.blockmaker.fdland.presentation.mypage.viewmodel.MyPageModifyViewModel

class MyPageModifyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyPageModifyBinding
    private val viewModel: MyPageModifyViewModel by viewModels() // MyPageModifyViewModel로 설정

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 데이터 바인딩 설정
        binding = ActivityMyPageModifyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ViewModel과 바인딩 연결
        binding.lifecycleOwner = this

        // 이전 버튼 클릭 시 MyPageConfirmActivity로 이동
        binding.toolbarPrevious.setOnClickListener {
            val intent = Intent(this, MyPageConfirmActivity::class.java)
            startActivity(intent)
            finish() // 현재 액티비티를 종료하여 뒤로가기 시 돌아오지 않도록 함
        }

        // 수정 사항 저장 버튼 클릭 리스너
        binding.btnEditInfo.setOnClickListener {
            val intent = Intent(this, MyPageConfirmActivity::class.java)
            startActivity(intent)
            finish()
        }

        // 저장 성공 여부를 관찰
        viewModel.saveSuccess.observe(this, Observer { success ->
            if (success) {
                Toast.makeText(this, "수정 사항이 저장되었습니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MyPageConfirmActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "저장에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}