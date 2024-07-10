package com.blockmaker.fdland.presentation.build.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.blockmaker.fdland.databinding.FragmentConstGallBinding
import com.blockmaker.fdland.presentation.build.viewmodel.ConstGalleryViewModel

class ConstGalleryActivity : AppCompatActivity() {

    private val constGalleryViewModel: ConstGalleryViewModel by viewModels()
    private lateinit var binding: FragmentConstGallBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentConstGallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarPrevious.setOnClickListener {
            val intent = Intent(this, ConstructActivity::class.java)
            startActivity(intent)
        }

        // 한 장의 이미지만 선택할 버튼과 이미지뷰 설정
        val button = binding.buttonLinearLayout1
        val imageView = binding.imageView1

        button.setOnClickListener {
            openImageChooser()
        }

        // 선택된 이미지를 관찰하고 이미지뷰에 설정하여 다음 페이지로 이동
        constGalleryViewModel.selectedImage.observe(this, Observer { uri ->
            uri?.let {
                imageView.setImageURI(it)
                moveToNextPage()
            }
        })

        // 네비게이션 플래그를 관찰하여 페이지 이동 처리
        constGalleryViewModel.navigateToNextPage.observe(this, Observer { shouldNavigate ->
            if (shouldNavigate) {
                moveToNextPage()
                constGalleryViewModel.resetNavigation()
            }
        })
    }

    // 이미지 선택 결과를 처리할 Activity Result Contract 설정
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { constGalleryViewModel.selectImage(it) }
    }

    // 이미지 선택기 열기
    private fun openImageChooser() {
        pickImageLauncher.launch("image/*")
    }

    // 다음 페이지로 이동하는 함수
    private fun moveToNextPage() {
        val intent = Intent(this, ConstLoadingView::class.java)
        startActivity(intent)
    }
}
