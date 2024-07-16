package com.blockmaker.fdland.presentation.build.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.blockmaker.fdland.databinding.FragmentConstGallBinding
import com.blockmaker.fdland.presentation.build.viewmodel.ConstGalleryViewModel
import com.blockmaker.fdland.presentation.common.ViewModelFactory
import com.bumptech.glide.Glide

class ConstGalleryActivity : AppCompatActivity() {

    private val viewModel: ConstGalleryViewModel by viewModels { ViewModelFactory() }
    private lateinit var binding: FragmentConstGallBinding

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { viewModel.selectImage(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentConstGallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupImageSelector()
        observeViewModel()
    }

    private fun setupToolbar() {
        binding.toolbarPrevious.setOnClickListener {
            startActivity(Intent(this, ConstructActivity::class.java))
        }
    }

    private fun setupImageSelector() {
        binding.buttonLinearLayout1.setOnClickListener {
            openImageChooser()
        }
    }

    private fun observeViewModel() {
        viewModel.selectedImage.observe(this, Observer { uri ->
            uri?.let {
                loadImage(it)
                viewModel.setConstImg(this, it)
            }
        })

        viewModel.navigateToNextPage.observe(this, Observer { shouldNavigate ->
            if (shouldNavigate) {
                moveToNextPage()
                viewModel.resetNavigation()
            }
        })

        viewModel.setConstImgIsSuccess.observe(this, Observer { isSuccess ->
            isSuccess?.let {
                if (it) {
                    moveToNextPage()
                } else {
                    handleUploadFailure()
                }
            }
        })
    }

    private fun openImageChooser() {
        pickImageLauncher.launch("image/*")
    }

    private fun loadImage(uri: Uri) {
        Glide.with(this).load(uri).into(binding.imageView1)
    }

    private fun moveToNextPage() {
        startActivity(Intent(this, ConstLoadingView::class.java))
    }

    private fun handleUploadFailure() {
        Toast.makeText(this, "이미지 업로드에 실패했습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
    }
}
