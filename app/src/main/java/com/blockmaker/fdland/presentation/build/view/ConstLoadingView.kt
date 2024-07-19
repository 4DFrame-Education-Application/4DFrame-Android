package com.blockmaker.fdland.presentation.build.view

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.blockmaker.fdland.databinding.FragmentConstLoadingBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class ConstLoadingView : AppCompatActivity() {
    private lateinit var binding: FragmentConstLoadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentConstLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 로딩 애니메이션과 프로그레스 바 활성화
        binding.homeAni.visibility = View.VISIBLE
        binding.progressBar.visibility = View.VISIBLE

        // Intent로부터 데이터를 받아옴
        val imageUrl = intent.getStringExtra("image_url")
        Log.d("ConstLoadingView", "Received Image URL: $imageUrl")

        if (imageUrl != null) {
            loadImage(imageUrl)
        } else {
            Toast.makeText(this, "이미지 URL을 받지 못했습니다.", Toast.LENGTH_SHORT).show()
            navigateToResultActivity(null)
        }
    }

    private fun loadImage(imageUrl: String) {
        Glide.with(this)
            .load(imageUrl)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.e("Glide", "Image load failed: $e")
                    e?.logRootCauses("Glide")
                    // 이미지 로드 실패 시 결과 화면으로 이동
                    navigateToResultActivity(null)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: com.bumptech.glide.load.DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.d("Glide", "Image load successful")
                    // 이미지 로드 성공 시 결과 화면으로 이동
                    navigateToResultActivity(imageUrl)
                    return false
                }
            })
            .submit() // 이미지를 실제로 로드하기 위해 submit()을 호출
    }

    private fun navigateToResultActivity(imageUrl: String?) {
        Log.d("ConstLoadingView", "Navigating to result activity with Image URL: $imageUrl")
        // 일정 시간 지연 이후 실행하기 위한 코드
        Handler(Looper.getMainLooper()).postDelayed({
            // 로딩 애니메이션 종료
            binding.homeAni.visibility = View.GONE
            binding.progressBar.visibility = View.GONE

            // 일정 시간이 지나면 결과 화면으로 이동
            val intent = Intent(this, ConstResultActivity::class.java).apply {
                putExtra("image_url", imageUrl) // image_url을 Intent에 추가
            }
            startActivity(intent)
            finish()
        }, 3000) // 로딩 시간: 3초
    }
}
