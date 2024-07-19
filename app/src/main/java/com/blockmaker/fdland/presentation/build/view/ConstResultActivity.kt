package com.blockmaker.fdland.presentation.build.view

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.blockmaker.fdland.databinding.ActivityConstResultBinding
import com.blockmaker.fdland.presentation.main.MainActivity

class ConstResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConstResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConstResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 블럭 쌓기 버튼 클릭 이벤트 처리
        binding.toolbarBtnAgain.setOnClickListener {
            val intent = Intent(this, ConstructActivity::class.java)
            startActivity(intent)
        }

        // 메인 화면으로 이동
        binding.toolbarBtnMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Intent로부터 데이터를 받아옴
        val imageUrl = intent.getStringExtra("image_url")
        Log.d("ConstResultActivity", "Received Image URL: $imageUrl")

        // 이미지를 표시
        if (imageUrl != null) {
            Glide.with(this)
                .load(imageUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.e("ConstResultActivity", "Image load failed: ${e?.message}")
                        e?.logRootCauses("ConstResultActivity")
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: com.bumptech.glide.load.DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.d("ConstResultActivity", "Image load successful")
                        return false
                    }
                })
                .into(binding.imageView1)
        } else {
            Log.e("ConstResultActivity", "Image URL is null")
        }
    }
}
