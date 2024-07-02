package com.blockmaker.Blockmaker.util.build

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.blockmaker.Blockmaker.R

class BuildGalleryActivity : AppCompatActivity() {

    private val PICK_IMAGE = 1
    private var currentImageView: ImageView? = null
    private var selectedImageCount = 0
    private val maxImageCount = 5

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_build_gall)

        val buttons = listOf(
            findViewById<LinearLayout>(R.id.buttonLinearLayout1),
            findViewById<LinearLayout>(R.id.buttonLinearLayout2),
            findViewById<LinearLayout>(R.id.buttonLinearLayout3),
            findViewById<LinearLayout>(R.id.buttonLinearLayout4),
            findViewById<LinearLayout>(R.id.buttonLinearLayout5)
        )

        val imageViews = listOf(
            findViewById<ImageView>(R.id.imageView1),
            findViewById<ImageView>(R.id.imageView2),
            findViewById<ImageView>(R.id.imageView3),
            findViewById<ImageView>(R.id.imageView4),
            findViewById<ImageView>(R.id.imageView5)
        )

        // 각 버튼과 이미지뷰를 연결하고 클릭 리스너를 설정
        buttons.zip(imageViews).forEach { (button, imageView) ->
            button.setOnClickListener {
                currentImageView = imageView
                openImageChooser()
            }
        }
    }

    // 이미지 선택을 위한 인텐트 실행
    private fun openImageChooser() {
        val intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }
        startActivityForResult(Intent.createChooser(intent, "Select a picture that fits the topic"), PICK_IMAGE)
    }

    // 이미지 선택 결과 처리
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val imageUri: Uri? = data.data
            currentImageView?.setImageURI(imageUri)
            selectedImageCount++
            if (selectedImageCount == maxImageCount) {
                moveToNextPage()
            }
        }
    }

    // 모든 이미지를 선택한 후 다음 페이지로 이동
    private fun moveToNextPage() {
        val intent = Intent(this, BuildLoadingView::class.java)
        startActivity(intent)
    }
}
