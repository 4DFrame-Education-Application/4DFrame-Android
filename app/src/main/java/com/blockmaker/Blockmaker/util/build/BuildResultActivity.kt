package com.blockmaker.Blockmaker.util.build

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.blockmaker.Blockmaker.R
import com.blockmaker.Blockmaker.presentation.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class BuildResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_build_result)

        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        val tabLayout: TabLayout = findViewById(R.id.tab_layout)

        viewPager.adapter = ViewPagerAdapter(this)

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
