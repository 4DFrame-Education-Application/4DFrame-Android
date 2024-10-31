package com.blockmaker.fdland.data.repository

import android.content.Context
import android.net.Uri
import com.blockmaker.fdland.data.api.BuildRetrofitInterface
import com.blockmaker.fdland.data.api.RetrofitClient
import com.blockmaker.fdland.data.repository.PathRepository.getRealPathFromURI
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class BuildRepository {

    private val apiService: BuildRetrofitInterface by lazy {
        RetrofitClient.getRetrofit().create(BuildRetrofitInterface::class.java)
    }

    // 5장의 이미지를 업로드하는 메서드
    fun uploadImages(
        context: Context,
        token: String,  // 토큰 추가
        photoUris: List<Uri>,
        onSuccess: (String) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        val fileParts = mutableListOf<MultipartBody.Part>()

        photoUris.forEachIndexed { index, uri ->
            val filePath = getRealPathFromURI(context, uri)
            if (filePath != null) {
                val file = File(filePath)
                val requestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val partName = when (index) {
                    0 -> "frontImage"
                    1 -> "backImage"
                    2 -> "leftImage"
                    3 -> "rightImage"
                    4 -> "upImage"
                    else -> "file$index"
                }
                fileParts.add(MultipartBody.Part.createFormData(partName, file.name, requestBody))
            } else {
                onFailure(Throwable("Invalid file path for URI: $uri"))
                return
            }
        }

        // 5장 이상의 이미지를 업로드할 수 있는지 확인
        if (fileParts.size >= 5) {
            apiService.uploadMultipleImages(
                token,  // 토큰을 헤더로 전달
                fileParts[0], fileParts[1], fileParts[2], fileParts[3], fileParts[4]  // 여러 장의 이미지 전달
            ).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()?.string()
                        if (responseBody != null) {
                            onSuccess(responseBody)
                        } else {
                            onFailure(Throwable("Response body is null"))
                        }
                    } else {
                        onFailure(Throwable("Upload failed: ${response.errorBody()?.string()}"))
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    onFailure(t)
                }
            })
        } else {
            onFailure(Throwable("Not enough photos to upload"))
        }
    }
}