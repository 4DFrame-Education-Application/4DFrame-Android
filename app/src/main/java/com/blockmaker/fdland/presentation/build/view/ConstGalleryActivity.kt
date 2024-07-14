package com.blockmaker.fdland.presentation.build.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.blockmaker.fdland.R
import com.blockmaker.fdland.data.repository.ConstRepository
import com.blockmaker.fdland.data.source.remote.construct.ConstructDataSourceImpl
import com.blockmaker.fdland.databinding.FragmentConstGallBinding
import com.blockmaker.fdland.presentation.build.viewmodel.ConstGalleryViewModel
import com.blockmaker.fdland.presentation.common.ViewModelFactory
import com.bumptech.glide.Glide

class ConstGalleryActivity : AppCompatActivity() {

    private val constRepository by lazy { ConstRepository(ConstructDataSourceImpl()) }
    private val constGalleryViewModel: ConstGalleryViewModel by viewModels {
        ViewModelFactory(constRepository)
    }

    private lateinit var binding: FragmentConstGallBinding

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
        constGalleryViewModel.selectedImage.observe(this, Observer { uri ->
            uri?.let {
                loadImage(it)
                constGalleryViewModel.setConstImg(this, it)
            }
        })

        constGalleryViewModel.navigateToNextPage.observe(this, Observer { shouldNavigate ->
            if (shouldNavigate) {
                moveToNextPage()
                constGalleryViewModel.resetNavigation()
            }
        })

        constGalleryViewModel.setConstImgIsSuccess.observe(this, Observer { isSuccess ->
            isSuccess?.let {
                if (it) {
                    moveToNextPage()
                } else {
                    handleUploadFailure()
                }
            }
        })
    }

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { constGalleryViewModel.selectImage(it) }
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
