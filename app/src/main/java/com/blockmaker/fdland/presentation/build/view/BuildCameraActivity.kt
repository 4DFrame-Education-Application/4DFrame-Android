package com.blockmaker.fdland.presentation.build.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.blockmaker.fdland.R
import com.blockmaker.fdland.presentation.build.viewmodel.BuildCameraViewModel

class BuildCameraActivity : AppCompatActivity() {

    private lateinit var previewView: PreviewView
    private lateinit var explainImageView: ImageView
    private lateinit var progressBar: ProgressBar
    private val viewModel: BuildCameraViewModel by viewModels()
    private val imageResources = listOf(
        R.drawable.explain_build_front,
        R.drawable.explain_build_back,
        R.drawable.explain_build_left,
        R.drawable.explain_build_right,
        R.drawable.explain_build_up,
        R.drawable.explain_build_up
    )
    private var imageIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_build_cam)

        previewView = findViewById(R.id.preview_view)
        explainImageView = findViewById(R.id.explain_image_view)
        progressBar = findViewById(R.id.progress_bar)
        val cameraButton: ImageButton = findViewById(R.id.button_cam)
        val buttonPrev: Button = findViewById(R.id.toolbar_previous)

        buttonPrev.setOnClickListener {
            startActivity(Intent(this, BuildActivity::class.java))
        }

        if (allPermissionsGranted()) {
            viewModel.startCamera(this, previewView)
        } else {
            requestPermissions()
        }

        viewModel.initialize()

        cameraButton.setOnClickListener {
            viewModel.takePhoto(this)
            updateExplainImageView()
        }

        viewModel.photoCount.observe(this, Observer { count ->
            if (count >= 5) {
                viewModel.uploadImages()
                startActivity(Intent(this, BuildResultActivity::class.java))
            }
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            progressBar.visibility = if (isLoading) ProgressBar.VISIBLE else ProgressBar.GONE
        })
    }

    private fun updateExplainImageView() {
        imageIndex = (imageIndex + 1) % imageResources.size
        explainImageView.setImageResource(imageResources[imageIndex])
    }

    private fun allPermissionsGranted(): Boolean {
        return REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.shutdown()
    }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                viewModel.startCamera(this, previewView)
            } else {
                Toast.makeText(this, "권한이 허용되지 않았습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
