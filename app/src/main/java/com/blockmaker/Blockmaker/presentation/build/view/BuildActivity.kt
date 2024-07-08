package com.blockmaker.Blockmaker.presentation.build.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.blockmaker.Blockmaker.R
import com.blockmaker.Blockmaker.presentation.build.viewmodel.BuildViewModel

class BuildActivity : AppCompatActivity() {

    private val buildViewModel: BuildViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_build)

        val buttonPrev: Button = findViewById(R.id.toolbar_previous)
        val buttonLinearLayout1: LinearLayout = findViewById(R.id.buttonLinearLayout1)
        val buttonLinearLayout2: LinearLayout = findViewById(R.id.buttonLinearLayout2)

        buttonPrev.setOnClickListener {
            buildViewModel.onPreviousButtonClick()
        }

        buttonLinearLayout1.setOnClickListener {
            buildViewModel.onCameraButtonClick()
        }

        buttonLinearLayout2.setOnClickListener {
            buildViewModel.onGalleryButtonClick()
        }

        buildViewModel.navigateToActivity.observe(this, Observer { activityClass ->
            activityClass?.let {
                val intent = Intent(this, it)
                startActivity(intent)
            }
        })
    }
}
