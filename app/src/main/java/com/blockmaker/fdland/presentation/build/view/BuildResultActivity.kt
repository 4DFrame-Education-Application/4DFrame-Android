//package com.blockmaker.fdland.presentation.build.view
//
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import android.widget.Button
//import androidx.activity.viewModels
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.Observer
//import androidx.viewpager2.widget.ViewPager2
//import com.blockmaker.fdland.R
//import com.blockmaker.fdland.presentation.adapter.ViewPagerAdapter
//import com.blockmaker.fdland.presentation.main.MainActivity
//import com.google.android.material.tabs.TabLayout
//import com.google.android.material.tabs.TabLayoutMediator
//import org.json.JSONArray
//import org.json.JSONObject
//
//class BuildResultActivity : AppCompatActivity() {
//    private val TAG = "BuildResultActivity"
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.fragment_build_result)
//
//        val imageUrls = intent.getStringArrayListExtra("imageUrls")
//        val jsonResponse = intent.getStringExtra("json_response")
//
//        // JSON 응답에서 results 부분만 추출하여 로그로 출력
//        if (!jsonResponse.isNullOrEmpty()) {
//            try {
//                val jsonObject = JSONObject(jsonResponse)
//                val resultsArray = jsonObject.getJSONArray("results") // JSONArray로 가져와야 합니다.
//                logResults(resultsArray)
//            } catch (e: Exception) {
//                Log.e(TAG, "JSON Parse Error: ", e)
//            }
//        }
//
//        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
//        val tabLayout: TabLayout = findViewById(R.id.tab_layout)
//
//        viewPager.adapter = ViewPagerAdapter(this, imageUrls ?: emptyList(), jsonResponse ?: "")
//
//        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//            tab.text = when (position) {
//                0 -> getString(R.string.build_front)
//                1 -> getString(R.string.build_back)
//                2 -> getString(R.string.build_left)
//                3 -> getString(R.string.build_right)
//                4 -> getString(R.string.build_up)
//                else -> getString(R.string.build_front)
//            }
//        }.attach()
//    }
//
//    private fun logResults(resultsArray: JSONArray) { // JSONArray 타입으로 받아야 합니다.
//        for (i in 0 until resultsArray.length()) {
//            val result = resultsArray.getJSONObject(i)
//            val name = result.getString("name")
//            val accuracy = result.getString("accuracy")
//            val rate = result.getString("rate")
//            Log.d(TAG, "Result $i - Name: $name, Accuracy: $accuracy, Rate: $rate")
//        }
//    }
//}
package com.blockmaker.fdland.presentation.build.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.blockmaker.fdland.R
import com.blockmaker.fdland.presentation.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.json.JSONArray
import org.json.JSONObject

class BuildResultActivity : AppCompatActivity() {
    private val TAG = "BuildResultActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_build_result)

        val imageUrls = intent.getStringArrayListExtra("imageUrls")
        val jsonResponse = intent.getStringExtra("json_response")

        // JSON 응답에서 results 부분만 추출하여 로그로 출력
        if (!jsonResponse.isNullOrEmpty()) {
            try {
                logResults(jsonResponse)
            } catch (e: Exception) {
                Log.e(TAG, "JSON Parse Error: ", e)
            }
        }

        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        val tabLayout: TabLayout = findViewById(R.id.tab_layout)

        viewPager.adapter = ViewPagerAdapter(this, imageUrls ?: emptyList(), jsonResponse ?: "")

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

    private fun logResults(jsonResponse: String) {
        try {
            val jsonObject = JSONObject(jsonResponse)
            val resultsArray = jsonObject.getJSONArray("results")
            for (i in 0 until resultsArray.length()) {
                val result = resultsArray.getJSONObject(i)
                val name = result.getString("name")
                val accuracy = result.getString("accuracy")
                val rate = result.getString("rate")

                // 각 결과를 로그로 출력
                Log.d(TAG, "Result $i - Name: $name, Accuracy: $accuracy, Rate: $rate")
            }
        } catch (e: Exception) {
            Log.e(TAG, "JSON Parse Error: ", e)
        }
    }
}



