package com.blockmaker.fdland.presentation.build.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.blockmaker.fdland.R
import com.blockmaker.fdland.presentation.build.adapter.ViewPagerAdapter
import com.blockmaker.fdland.presentation.build.viewmodel.BuildResultViewModel
import com.blockmaker.fdland.presentation.main.MainActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class BuildResultActivity : AppCompatActivity() {

    private val viewModel: BuildResultViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_build_result)

        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        val tabLayout: TabLayout = findViewById(R.id.tab_layout)
        val buttonPrev: Button = findViewById(R.id.toolbar_btn_again)
        val buttonMain: Button = findViewById(R.id.toolbar_btn_main)

        // intent를 통해 전달받은 데이터 수신
        val imageUrls = intent.getStringArrayListExtra("imageUrls")
        val jsonResponse = intent.getStringExtra("json_response")

        // ViewModel에 데이터 설정
        viewModel.setImageUrls(imageUrls ?: emptyList())
        viewModel.setJsonResponse(jsonResponse ?: "")

        // ViewPagerAdapter 설정 및 TabLayoutMediator 부착
        viewModel.imageUrls.observe(this, Observer { urls ->
            viewModel.jsonResponse.observe(this, Observer { json ->
                viewPager.adapter = ViewPagerAdapter(this, urls, json)
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
            })
        })

        // toolbar_btn_again 클릭 시 데이터 초기화 및 이전 화면으로 이동
        buttonPrev.setOnClickListener {
            viewModel.clearData()  // ViewModel의 데이터를 초기화
            viewModel.onPrevButtonClicked()
        }

        // toolbar_btn_main 클릭 시 메인 화면으로 이동
        buttonMain.setOnClickListener {
            viewModel.onMainButtonClicked()
        }

        // 이전 화면으로 이동
        viewModel.navigateToBuildActivity.observe(this, Observer { navigate ->
            if (navigate) {
                val intent = Intent(this, BuildActivity::class.java)
                startActivity(intent)
                viewModel.onNavigationHandled()  // 내비게이션 처리 완료
            }
        })

        // 메인 화면으로 이동
        viewModel.navigateToMainActivity.observe(this, Observer { navigate ->
            if (navigate) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                viewModel.onNavigationHandled()  // 내비게이션 처리 완료
            }
        })
    }
}