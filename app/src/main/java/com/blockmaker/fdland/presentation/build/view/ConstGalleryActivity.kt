package com.blockmaker.fdland.presentation.build.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.blockmaker.fdland.data.repository.ConstRepository
import com.blockmaker.fdland.data.source.remote.construct.ConstructDataSourceImpl
import com.blockmaker.fdland.databinding.FragmentConstGallBinding
import com.blockmaker.fdland.presentation.build.viewmodel.ConstGalleryViewModel
import com.blockmaker.fdland.presentation.common.ViewModelFactory
import com.bumptech.glide.Glide

class ConstGalleryActivity : AppCompatActivity() {

    private lateinit var binding: FragmentConstGallBinding
    private lateinit var constGalleryViewModel: ConstGalleryViewModel

    private lateinit var token: String  // 토큰을 저장할 변수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentConstGallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // SharedPreferences에서 토큰 가져오기
        token = getToken()

        // ViewModel 초기화
        val factory = ViewModelFactory(ConstRepository(ConstructDataSourceImpl()))
        constGalleryViewModel = ViewModelProvider(this, factory).get(ConstGalleryViewModel::class.java)

        // 이제 ViewModel이 초기화되었으므로 안전하게 메서드를 호출할 수 있습니다.
        observeSelectedImage()

        setupToolbar()
        setupImageSelection()
        observeImageUploadResult()
    }

    private fun setupToolbar() {
        binding.toolbarPrevious.setOnClickListener {
            val intent = Intent(this, ConstructActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupImageSelection() {
        binding.buttonLinearLayout1.setOnClickListener {
            openImageChooser()
        }
    }

    private fun observeSelectedImage() {
        constGalleryViewModel.selectedImage.observe(this, Observer { uri ->
            uri?.let {
                Glide.with(this)
                    .load(it)
                    .into(binding.imageView1)
            }
        })
    }

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

    private fun openImageChooser() {
        pickImageLauncher.launch("image/*")
    }

    private fun moveToNextPage(imageUrl: String?) {
        val intent = Intent(this, ConstLoadingView::class.java).apply {
            putExtra("image_url", imageUrl)
        }
        startActivity(intent)
    }

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            constGalleryViewModel.selectImage(it, token, this)  // 토큰을 전달
        }
    }

    // SharedPreferences에서 토큰을 가져오는 메서드
    private fun getToken(): String {
        val sharedPreferences = getSharedPreferences("auth_prefs", MODE_PRIVATE)
        val token = sharedPreferences.getString("X-AUTH-TOKEN", null) ?: ""
        Log.d("ConstGalleryActivity", "가져온 토큰: $token") // 가져온 토큰 로그 출력
        return token
    }
}