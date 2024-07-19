package com.blockmaker.fdland.presentation.build.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.blockmaker.fdland.data.repository.ConstRepository
import com.blockmaker.fdland.data.source.remote.construct.ConstructDataSourceImpl
import com.blockmaker.fdland.databinding.FragmentConstGallBinding
import com.blockmaker.fdland.presentation.build.viewmodel.ConstGalleryViewModel
import com.blockmaker.fdland.presentation.common.ViewModelFactory
import com.bumptech.glide.Glide

class ConstGalleryActivity : AppCompatActivity() {

    // 리포지토리 설정
    private val constRepository by lazy {
        ConstRepository(ConstructDataSourceImpl())
    }

    // ViewModel 설정
    private val constGalleryViewModel: ConstGalleryViewModel by viewModels {
        ViewModelFactory(constRepository)
    }

    private lateinit var binding: FragmentConstGallBinding

    // 이미지 선택을 위한 ActivityResultLauncher 설정
    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                constGalleryViewModel.selectImage(it, this.applicationContext)  // application context를 전달
                Glide.with(this)
                    .load(it)
                    .into(binding.imageView1)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentConstGallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupImageSelection()
        observeSelectedImage()
        observeImageUploadResult()
    }

    // 툴바 설정
    private fun setupToolbar() {
        binding.toolbarPrevious.setOnClickListener {
            val intent = Intent(this, ConstructActivity::class.java)
            startActivity(intent)
        }
    }

    // 이미지 선택 버튼 설정
    private fun setupImageSelection() {
        binding.buttonLinearLayout1.setOnClickListener {
            openImageChooser()
        }
    }

    // 선택된 이미지 관찰
    private fun observeSelectedImage() {
        constGalleryViewModel.selectedImage.observe(this, Observer { uri ->
            uri?.let {
                Glide.with(this)
                    .load(it)
                    .into(binding.imageView1)
            }
        })
    }

    // 이미지 업로드 결과 관찰
    private fun observeImageUploadResult() {
        constGalleryViewModel.setConstImgIsSuccess.observe(this, Observer { result ->
            result?.let {
                if (it.isSuccess) {
                    moveToNextPage(it.imageUrl)  // 업로드 성공 시 다음 페이지로 이동
                } else {
                    Toast.makeText(this, "Image upload failed", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    // 이미지 선택기 열기
    private fun openImageChooser() {
        pickImageLauncher.launch("image/*")
    }

    // 다음 페이지로 이동
    private fun moveToNextPage(imageUrl: String?) {
        val intent = Intent(this, ConstLoadingView::class.java).apply {
            putExtra("image_url", imageUrl)
        }
        startActivity(intent)
    }
}
