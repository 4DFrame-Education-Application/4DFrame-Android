package com.blockmaker.fdland.presentation.build.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.blockmaker.fdland.data.Repository.ConstRepository
import com.blockmaker.fdland.data.source.remote.construct.ConstructDataSourceImpl
import com.blockmaker.fdland.databinding.FragmentConstGallBinding
import com.blockmaker.fdland.presentation.build.viewmodel.ConstGalleryViewModel
import com.blockmaker.fdland.presentation.common.ViewModelFactory
import com.bumptech.glide.Glide
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class ConstGalleryActivity : AppCompatActivity() {

    private val constRepository by lazy {
        ConstRepository(ConstructDataSourceImpl())
    }

    private val constGalleryViewModel: ConstGalleryViewModel by viewModels {
        ViewModelFactory(constRepository)
    }

    private lateinit var binding: FragmentConstGallBinding

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            constGalleryViewModel.selectImage(it)
            Glide.with(this)
                .load(it)
                .into(binding.imageView1)
            prepareAndSendImage(it)  // Prepare image and send to server
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentConstGallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupImageSelection()
        observeSelectedImage()
        observeNavigation()
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

    private fun observeNavigation() {
        constGalleryViewModel.navigateToNextPage.observe(this, Observer { shouldNavigate ->
            if (shouldNavigate) {
                moveToNextPage()
                constGalleryViewModel.resetNavigation()
            }
        })
    }

    private fun observeImageUploadResult() {
        constGalleryViewModel.setConstImgIsSuccess.observe(this, Observer { isSuccess ->
            isSuccess?.let {
                if (it) {
                    Toast.makeText(this, "Image upload successful", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Image upload failed", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun openImageChooser() {
        pickImageLauncher.launch("image/*")
    }

    private fun moveToNextPage() {
        val intent = Intent(this, ConstLoadingView::class.java)
        startActivity(intent)
    }

    private fun prepareAndSendImage(uri: Uri) {
        val file = File(uri.path)
        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        val body = MultipartBody.Part.createFormData("profile", file.name, requestFile)

        constGalleryViewModel.setConstImg("your_access_token_here", body)
    }
}
