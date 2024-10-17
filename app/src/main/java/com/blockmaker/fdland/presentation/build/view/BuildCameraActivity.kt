package com.blockmaker.fdland.presentation.build.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.blockmaker.fdland.R
import com.blockmaker.fdland.data.repository.PhotoRepository
import com.blockmaker.fdland.presentation.build.viewmodel.BuildCameraViewModel

class BuildCameraActivity : AppCompatActivity() {

    private lateinit var previewView: PreviewView
    private lateinit var explainImageView: ImageView
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
    private lateinit var token: String  // 토큰 변수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_build_cam)

        // SharedPreferences에서 토큰 가져오기
        token = getToken()

        PhotoRepository.clear()  // PhotoRepository의 photoUris 리스트 초기화

        previewView = findViewById(R.id.preview_view)
        explainImageView = findViewById(R.id.explain_image_view)
        val cameraButton: ImageButton = findViewById(R.id.button_cam)
        val buttonPrev: Button = findViewById(R.id.toolbar_previous)

        buttonPrev.setOnClickListener {
            val intent = Intent(this, BuildActivity::class.java)
            startActivity(intent)
        }

        if (allPermissionsGranted()) {
            viewModel.startCamera(this, previewView)
        } else {
            requestPermissions()
        }

        viewModel.initialize()

        cameraButton.setOnClickListener {
            viewModel.takePhoto(this, token)  // 토큰을 함께 전달
            updateExplainImageView()
        }

        viewModel.navigateToNextPage.observe(this) { shouldNavigate ->
            if (shouldNavigate) {
                moveToLoadingView()
                viewModel.resetNavigation()
            }
        }
    }

    private fun updateExplainImageView() {
        imageIndex = (imageIndex + 1) % imageResources.size
        explainImageView.setImageResource(imageResources[imageIndex])
    }

    private fun getToken(): String {
        val sharedPreferences = getSharedPreferences("auth_prefs", MODE_PRIVATE)
        return sharedPreferences.getString("X-AUTH-TOKEN", "") ?: ""
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

    private fun moveToLoadingView() {
        val intent = Intent(this, BuildLoadingView::class.java)
        startActivity(intent)
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