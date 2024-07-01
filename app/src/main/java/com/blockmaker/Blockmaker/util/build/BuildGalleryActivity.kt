package com.blockmaker.Blockmaker.util.build

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
    private var currentLayout: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_build_gall)

        val button1 = findViewById<LinearLayout>(R.id.buttonLinearLayout1)
        val button2 = findViewById<LinearLayout>(R.id.buttonLinearLayout2)
        val button3 = findViewById<LinearLayout>(R.id.buttonLinearLayout3)
        val button4 = findViewById<LinearLayout>(R.id.buttonLinearLayout4)
        val button5 = findViewById<LinearLayout>(R.id.buttonLinearLayout5)

        val buttons = listOf(button1, button2, button3, button4, button5)
        buttons.forEach { button ->
            button.setOnClickListener {
                currentLayout = button
                openImageChooser()
            }
        }
    }

    private fun openImageChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "주제에 맞는 사진을 선택하세요"), PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val imageUri: Uri? = data.data
            val imageView = currentLayout?.findViewById<ImageView>(R.id.imageView)
            imageView?.setImageURI(imageUri)
        }
    }

}
