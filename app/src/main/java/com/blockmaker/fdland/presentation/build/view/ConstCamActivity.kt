package com.blockmaker.fdland.presentation.build.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.blockmaker.fdland.R
import com.blockmaker.fdland.data.repository.ConstRepository
import com.blockmaker.fdland.presentation.build.viewmodel.ConstCamViewModel
import com.blockmaker.fdland.presentation.common.ViewModelFactory

class ConstCamActivity : AppCompatActivity(), LifecycleOwner {

    private lateinit var previewView: PreviewView
    private val viewModel: ConstCamViewModel by viewModels { ViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_const_cam)

        previewView = findViewById(R.id.preview_view)
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
            viewModel.takePhoto(this)
        }
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
