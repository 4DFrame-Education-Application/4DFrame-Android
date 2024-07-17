//package com.blockmaker.fdland.presentation.build.view
//
//import android.content.Intent
//import android.os.Bundle
//import android.os.Handler
//import android.os.Looper
//import android.view.View
//import androidx.appcompat.app.AppCompatActivity
//import com.blockmaker.fdland.databinding.FragmentBuildLoadingBinding
//
//class BuildLoadingView : AppCompatActivity() {
//
//    private lateinit var binding: FragmentBuildLoadingBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = FragmentBuildLoadingBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        // 로딩 애니메이션과 프로그레스 바 활성화
//        binding.homeAni.visibility = View.VISIBLE
//        binding.progressBar.visibility = View.VISIBLE
//
//        // 일정 시간 지연 이후 실행하기 위한 코드
//        Handler(Looper.getMainLooper()).postDelayed({
//            // 로딩 애니메이션 종료
//            binding.homeAni.visibility = View.GONE
//            binding.progressBar.visibility = View.GONE
//
//            // 일정 시간이 지나면 결과 화면으로 이동
//            val intent = Intent(this, BuildResultActivity::class.java)
//            startActivity(intent)
//        }, 3000) // 로딩 시간: 3초
//    }
//}
//package com.blockmaker.fdland.presentation.build.view
//
//import android.content.Intent
//import android.os.Bundle
//import android.os.Handler
//import android.os.Looper
//import android.util.Log
//import android.view.View
//import androidx.appcompat.app.AppCompatActivity
//import com.blockmaker.fdland.R
//import com.blockmaker.fdland.databinding.FragmentBuildLoadingBinding
//
//class BuildLoadingView : AppCompatActivity() {
//
//    private lateinit var binding: FragmentBuildLoadingBinding
//    private val TAG = "BuildLoadingView"
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = FragmentBuildLoadingBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        // 로딩 애니메이션과 프로그레스 바 활성화
//        binding.homeAni.visibility = View.VISIBLE
//        binding.progressBar.visibility = View.VISIBLE
//
//        val imageUrls = intent.getStringArrayListExtra("imageUrls")
//        val jsonResponse = intent.getStringExtra("json_response")
//
//        if (jsonResponse.isNullOrEmpty()) {
//            Log.e(TAG, "JSON data is empty or null")
//            return
//        }
//
//        Log.d(TAG, "Full JSON Response: $jsonResponse")
//
//        if (imageUrls != null && imageUrls.isNotEmpty()) {
//            Log.d(TAG, "Received imageUrls: $imageUrls")
//            for (imageUrl in imageUrls) {
//                Log.d(TAG, "Received imageUrl: $imageUrl")
//            }
//            navigateToResultActivity(imageUrls, jsonResponse)
//        } else {
//            Log.d(TAG, "No imageUrls received")
//            // 일정 시간 지연 후 다시 확인
//            Handler(Looper.getMainLooper()).postDelayed({
//                val newImageUrls = intent.getStringArrayListExtra("imageUrls")
//                if (newImageUrls != null && newImageUrls.isNotEmpty()) {
//                    Log.d(TAG, "Received imageUrls after delay: $newImageUrls")
//                    navigateToResultActivity(newImageUrls, jsonResponse)
//                } else {
//                    Log.d(TAG, "Still no imageUrls received")
//                    // 로딩 애니메이션 종료
//                    binding.homeAni.visibility = View.GONE
//                    binding.progressBar.visibility = View.GONE
//                }
//            }, 5000) // 5초 후에 다시 확인
//        }
//    }
//
//    private fun navigateToResultActivity(imageUrls: ArrayList<String>, jsonResponse: String?) {
//        // 로딩 애니메이션 종료
//        binding.homeAni.visibility = View.GONE
//        binding.progressBar.visibility = View.GONE
//
//        val intent = Intent(this, BuildResultActivity::class.java).apply {
//            putStringArrayListExtra("imageUrls", imageUrls)
//            putExtra("json_response", jsonResponse) // JSON 결과 전달
//        }
//        startActivity(intent)
//        finish()
//    }
//}

package com.blockmaker.fdland.presentation.build.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.blockmaker.fdland.databinding.FragmentBuildLoadingBinding

class BuildLoadingView : AppCompatActivity() {

    private lateinit var binding: FragmentBuildLoadingBinding
    private val TAG = "BuildLoadingView"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentBuildLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 로딩 애니메이션과 프로그레스 바 활성화
        binding.homeAni.visibility = View.VISIBLE
        binding.progressBar.visibility = View.VISIBLE

        val imageUrls = intent.getStringArrayListExtra("imageUrls")
        val jsonResponse = intent.getStringExtra("json_response")

        if (jsonResponse.isNullOrEmpty()) {
            Log.e(TAG, "JSON data is empty or null")
            return
        }

        Log.d(TAG, "Full JSON Response: $jsonResponse")

        if (imageUrls != null && imageUrls.isNotEmpty()) {
            Log.d(TAG, "Received imageUrls: $imageUrls")
            for (imageUrl in imageUrls) {
                Log.d(TAG, "Received imageUrl: $imageUrl")
            }
            navigateToResultActivity(imageUrls, jsonResponse)
        } else {
            Log.d(TAG, "No imageUrls received")
            // 일정 시간 지연 후 다시 확인
            Handler(Looper.getMainLooper()).postDelayed({
                val newImageUrls = intent.getStringArrayListExtra("imageUrls")
                if (newImageUrls != null && newImageUrls.isNotEmpty()) {
                    Log.d(TAG, "Received imageUrls after delay: $newImageUrls")
                    navigateToResultActivity(newImageUrls, jsonResponse)
                } else {
                    Log.d(TAG, "Still no imageUrls received")
                    // 로딩 애니메이션 종료
                    binding.homeAni.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                }
            }, 5000) // 5초 후에 다시 확인
        }
    }

    private fun navigateToResultActivity(imageUrls: ArrayList<String>, jsonResponse: String?) {
        // 로딩 애니메이션 종료
        binding.homeAni.visibility = View.GONE
        binding.progressBar.visibility = View.GONE

        val intent = Intent(this, BuildResultActivity::class.java).apply {
            putStringArrayListExtra("imageUrls", imageUrls)
            putExtra("json_response", jsonResponse) // JSON 결과 전달
        }
        startActivity(intent)
        finish()
    }
}
