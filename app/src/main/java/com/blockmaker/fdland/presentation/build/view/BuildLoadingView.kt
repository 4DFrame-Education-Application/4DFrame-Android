package com.blockmaker.fdland.presentation.build.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.blockmaker.fdland.databinding.FragmentBuildLoadingBinding
import org.json.JSONObject

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
            Log.d(TAG, "이미지가 온 게 없습니다.")
            // 일정 시간 지연 후 다시 확인
            Handler(Looper.getMainLooper()).postDelayed({
                val newImageUrls = parseImageUrls(jsonResponse)
                if (newImageUrls.isNotEmpty()) {
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

    private fun parseImageUrls(jsonResponse: String): ArrayList<String> {
        val imageUrls = ArrayList<String>()
        try {
            val jsonObject = JSONObject(jsonResponse)
            val frontImage = jsonObject.optString("front_image")
            val backImage = jsonObject.optString("back_image")
            val leftImage = jsonObject.optString("left_image")
            val rightImage = jsonObject.optString("right_image")
            val upImage = jsonObject.optString("up_image")

            if (frontImage.isNotEmpty()) imageUrls.add(frontImage)
            if (backImage.isNotEmpty()) imageUrls.add(backImage)
            if (leftImage.isNotEmpty()) imageUrls.add(leftImage)
            if (rightImage.isNotEmpty()) imageUrls.add(rightImage)
            if (upImage.isNotEmpty()) imageUrls.add(upImage)

        } catch (e: Exception) {
            Log.e(TAG, "Error parsing JSON response: ", e)
        }
        return imageUrls
    }
}
