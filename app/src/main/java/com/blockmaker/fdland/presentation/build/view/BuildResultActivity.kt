package com.blockmaker.fdland.presentation.build.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.blockmaker.fdland.R
import com.blockmaker.fdland.presentation.build.adapter.ViewPagerAdapter
import com.blockmaker.fdland.presentation.main.MainActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class BuildResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_build_result)

        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        val tabLayout: TabLayout = findViewById(R.id.tab_layout)
        val button_prev: Button = findViewById(R.id.toolbar_btn_again)
        val button_main: Button = findViewById(R.id.toolbar_btn_main)

        viewPager.adapter = ViewPagerAdapter(this)

        button_prev.setOnClickListener {
            val intent: Intent = Intent(this, BuildActivity::class.java)
            startActivity(intent)
        }

        button_main.setOnClickListener {
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.build_front)
                1 -> getString(R.string.build_back)
                2 -> getString(R.string.build_left)
                3 -> getString(R.string.build_right)
                4 -> getString(R.string.build_up)
                else -> getString(R.string.build_front)
            }
        }.attach()
    }
}
