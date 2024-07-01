package com.blockmaker.Blockmaker.util.build

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.blockmaker.Blockmaker.R
import com.blockmaker.Blockmaker.util.build.BuildCameraActivity
import com.blockmaker.Blockmaker.util.build.BuildGalleryActivity

class BuildActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_build)

        // 카메라 버튼 클릭 이벤트 처리
        val buttonLinearLayout1: LinearLayout = findViewById(R.id.buttonLinearLayout1)
        buttonLinearLayout1.setOnClickListener {
            val intent = Intent(this, BuildCameraActivity::class.java)
            startActivity(intent)
        }

        // 갤러리 버튼 클릭 이벤트 처리
        val buttonLinearLayout2: LinearLayout = findViewById(R.id.buttonLinearLayout2)
        buttonLinearLayout2.setOnClickListener {
            val intent = Intent(this, BuildGalleryActivity::class.java)
            startActivity(intent)
        }
    }
}