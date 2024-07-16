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

    private val ConstViewModel: ConstViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_const)

        val buttonPrev: Button = findViewById(R.id.toolbar_previous)
        val buttonLinearLayout1: LinearLayout = findViewById(R.id.buttonLinearLayout1)
        val buttonLinearLayout2: LinearLayout = findViewById(R.id.buttonLinearLayout2)

        buttonPrev.setOnClickListener {
            ConstViewModel.onPreviousButtonClick()
        }

        buttonLinearLayout1.setOnClickListener {
            ConstViewModel.onCameraButtonClick()
        }

        buttonLinearLayout2.setOnClickListener {
            ConstViewModel.onGalleryButtonClick()
        }

        ConstViewModel.navigateToActivity.observe(this, Observer { activityClass ->
            activityClass?.let {
                val intent = Intent(this, it)
                startActivity(intent)
            }
        })
    }
}
