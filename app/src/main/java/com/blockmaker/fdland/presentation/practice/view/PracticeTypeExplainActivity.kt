package com.blockmaker.fdland.presentation.practice.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.blockmaker.fdland.R
import com.blockmaker.fdland.presentation.practice.adapter.PracticeViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class PracticeTypeExplainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_type_explain)

        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        val tabLayout: TabLayout = findViewById(R.id.tab_layout)
        val buttonPrev: Button = findViewById(R.id.toolbar_previous)

        viewPager.adapter = PracticeViewPagerAdapter(this)

        buttonPrev.setOnClickListener {
            val intent = Intent(this, PracticePickerActivity::class.java)
            startActivity(intent)
        }

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.type_original)
                1 -> getString(R.string.type_step)
                2 -> getString(R.string.type_together)
                3 -> getString(R.string.type_change)
                4 -> getString(R.string.type_zigzag)
                5 -> getString(R.string.type_stand)
                6 -> getString(R.string.type_cross)
                7 -> getString(R.string.type_turn)
                8 -> getString(R.string.type_lean)
                9 -> getString(R.string.type_askew)
                else -> getString(R.string.type_original)
            }
        }.attach()
    }
}
