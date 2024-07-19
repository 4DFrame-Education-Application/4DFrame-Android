package com.blockmaker.fdland.presentation.build.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.blockmaker.fdland.R
import com.blockmaker.fdland.presentation.build.viewmodel.ConstViewModel

class ConstructActivity : AppCompatActivity() {

    private val constViewModel: ConstViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_const)

        val buttonPrev: Button = findViewById(R.id.toolbar_previous)
        val buttonLinearLayout1: LinearLayout = findViewById(R.id.buttonLinearLayout1)
        val buttonLinearLayout2: LinearLayout = findViewById(R.id.buttonLinearLayout2)

        buttonPrev.setOnClickListener {
            constViewModel.onPreviousButtonClick()
        }

        buttonLinearLayout1.setOnClickListener {
            constViewModel.onCameraButtonClick()
        }

        buttonLinearLayout2.setOnClickListener {
            constViewModel.onGalleryButtonClick()
        }

        constViewModel.navigateToActivity.observe(this, Observer { activityClass ->
            activityClass?.let {
                val intent = Intent(this, it)
                startActivity(intent)
            }
        })
    }

    // 생명 주기 메서드 추가
    override fun onPause() {
        super.onPause()
        // 액티비티가 일시 정지될 때 수행할 작업
    }

    override fun onStop() {
        super.onStop()
        // 액티비티가 중지될 때 수행할 작업
    }

    override fun onDestroy() {
        super.onDestroy()
        // 액티비티가 파괴될 때 수행할 작업
    }
}
