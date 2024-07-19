package com.blockmaker.fdland.presentation.build.view

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.blockmaker.fdland.databinding.FragmentBuildGallBinding
import com.blockmaker.fdland.presentation.build.viewmodel.BuildGalleryViewModel

class BuildGalleryActivity : AppCompatActivity() {

    private val buildGalleryViewModel: BuildGalleryViewModel by viewModels()
    private lateinit var binding: FragmentBuildGallBinding

    private var currentIndex: Int? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentBuildGallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarPrevious.setOnClickListener {
            val intent = Intent(this, BuildActivity::class.java)
            startActivity(intent)
        }

        val buttons = listOf(
            binding.buttonLinearLayout1,
            binding.buttonLinearLayout2,
            binding.buttonLinearLayout3,
            binding.buttonLinearLayout4,
            binding.buttonLinearLayout5
        )

        val imageViews = listOf(
            binding.imageView1,
            binding.imageView2,
            binding.imageView3,
            binding.imageView4,
            binding.imageView5
        )

        // 각 버튼과 이미지뷰를 연결하고 클릭 리스너를 설정
        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                currentIndex = index
                openImageChooser()
            }
        }

        buildGalleryViewModel.selectedImages.observe(this, Observer { uris ->
            uris.forEachIndexed { index, uri ->
                if (index < imageViews.size) {
                    imageViews[index].setImageURI(uri)
                }
            }
        })

        buildGalleryViewModel.navigateToNextPage.observe(this, Observer { shouldNavigate ->
            if (shouldNavigate) {
                moveToNextPage()
                buildGalleryViewModel.resetNavigation()
            }
        })
    }

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { currentIndex?.let { _ -> buildGalleryViewModel.selectImage(it,this) } }
    }

    private fun openImageChooser() {
        pickImageLauncher.launch("image/*")
    }

    private fun moveToNextPage() {
        val intent = Intent(this, BuildLoadingView::class.java)
        startActivity(intent)
    }
}