package com.blockmaker.fdland.presentation.build.view

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.blockmaker.fdland.data.repository.ConstRepository
import com.blockmaker.fdland.data.source.remote.construct.ConstructDataSourceImpl
import com.blockmaker.fdland.databinding.FragmentConstLoadingBinding
import com.blockmaker.fdland.data.repository.PathRepository
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File

class ConstLoadingView : AppCompatActivity() {
    private lateinit var binding: FragmentConstLoadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentConstLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.homeAni.visibility = View.VISIBLE
        binding.progressBar.visibility = View.VISIBLE

        val uriString = intent.getStringExtra("local_uri")
        val token = intent.getStringExtra("token")

        if (uriString != null && token != null) {
            val uri = Uri.parse(uriString)
            uploadImageToServer(uri, token) // ✅ 함수 이름 정확히 일치시킴
        } else {
            Toast.makeText(this, "이미지 URI 또는 토큰이 없습니다.", Toast.LENGTH_SHORT).show()
            navigateToResultActivity(null)
        }
    }

    private fun uploadImageToServer(uri: Uri, token: String) {
        lifecycleScope.launch {
            try {
                val filePath = PathRepository.getRealPathFromURI(this@ConstLoadingView, uri)
                if (filePath == null) {
                    Log.e("ConstLoadingView", "파일 경로를 가져올 수 없습니다.")
                    navigateToResultActivity(null)
                    return@launch
                }

                val file = File(filePath)
                val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val body = MultipartBody.Part.createFormData("composition_image", file.name, requestFile)

                val response = ConstRepository(ConstructDataSourceImpl()).setConstImg(token, body)
                if (response.isSuccessful) {
                    val responseBody = response.body()?.string()
                    val imageUrl = extractImageUrl(responseBody)
                    if (imageUrl.isNotEmpty()) {
                        Log.d("ConstLoadingView", "Image uploaded. URL: $imageUrl")
                        loadImage(imageUrl)
                    } else {
                        Log.e("ConstLoadingView", "image_url이 응답에 없음")
                        navigateToResultActivity(null)
                    }
                } else {
                    Log.e("ConstLoadingView", "업로드 실패: ${response.errorBody()?.string()}")
                    navigateToResultActivity(null)
                }
            } catch (e: Exception) {
                Log.e("ConstLoadingView", "업로드 중 예외 발생: ${e.message}", e)
                navigateToResultActivity(null)
            }
        }
    }

    private fun extractImageUrl(responseBody: String?): String {
        return try {
            val jsonObject = JSONObject(responseBody ?: "{}")
            jsonObject.optString("image_url", "")
        } catch (e: Exception) {
            Log.e("ConstLoadingView", "JSON 파싱 오류: ${e.message}")
            ""
        }
    }

    private fun loadImage(imageUrl: String) {
        Glide.with(this)
            .load(imageUrl)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean
                ): Boolean {
                    Log.e("Glide", "Image load failed: $e")
                    // 로딩 실패해도 imageUrl은 정상 응답이므로 전달
                    navigateToResultActivity(imageUrl)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?, model: Any?, target: Target<Drawable>?,
                    dataSource: com.bumptech.glide.load.DataSource?, isFirstResource: Boolean
                ): Boolean {
                    Log.d("Glide", "Image loaded successfully")
                    navigateToResultActivity(imageUrl)
                    return false
                }
            })
            .submit()
    }

    private fun navigateToResultActivity(imageUrl: String?) {
        Handler(Looper.getMainLooper()).postDelayed({
            binding.homeAni.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
            val intent = Intent(this, ConstResultActivity::class.java).apply {
                putExtra("image_url", imageUrl)
            }
            startActivity(intent)
            finish()
        }, 3000)
    }
}