package com.blockmaker.fdland.presentation.build.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.blockmaker.fdland.data.repository.ConstRepository
import com.blockmaker.fdland.data.source.remote.construct.ConstructDataSourceImpl
import com.blockmaker.fdland.databinding.ActivityConstGallBinding
import com.blockmaker.fdland.presentation.build.viewmodel.ConstGalleryViewModel

class ConstGalleryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConstGallBinding
    private lateinit var viewModel: ConstGalleryViewModel

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            val token = getTokenFromPreferences()
            Log.d("ConstGalleryActivity", "Picked URI: $uri, token: $token")
            navigateToLoadingView(uri, token)
        } else {
            Toast.makeText(this, "이미지를 선택하지 않았습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConstGallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ConstGalleryViewModel(ConstRepository(ConstructDataSourceImpl()))

        binding.imageView1.setOnClickListener {
            pickImageFromGallery()
        }

        binding.toolbarPrevious.setOnClickListener {
            finish()
        }
    }

    private fun pickImageFromGallery() {
        pickImageLauncher.launch("image/*")
    }

    private fun getTokenFromPreferences(): String {
        val prefs = getSharedPreferences("auth_prefs", MODE_PRIVATE)
        return prefs.getString("X-AUTH-TOKEN", "") ?: ""
    }

    private fun navigateToLoadingView(uri: Uri, token: String) {
        val intent = Intent(this, ConstLoadingView::class.java).apply {
            putExtra("local_uri", uri.toString())
            putExtra("token", token)
        }
        startActivity(intent)
    }
}