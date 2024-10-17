package com.blockmaker.fdland.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.blockmaker.fdland.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MyPageBuildListResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_build_list_result)

        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        val tabLayout: TabLayout = findViewById(R.id.tab_layout)
        val buttonAgain: Button = findViewById(R.id.toolbar_btn_again)
        val buttonMain : Button = findViewById(R.id.toolbar_btn_main)

        viewPager.adapter = MyPageResultViewPagerAdapter(this)

        buttonAgain.setOnClickListener {
            val intent = Intent(this, MyPageResulttoBuildActivity::class.java)
            startActivity(intent)
        }

        buttonMain.setOnClickListener {
            val intent = Intent(this, MyPageResultMainActivity::class.java)
            startActivity(intent)
        }

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "앞쪽"
                1 -> "뒤쪽"
                2 -> "오른쪽"
                3 -> "왼쪽"
                4 -> "위쪽"
                else -> "앞쪽"
            }
        }.attach()
    }
}
