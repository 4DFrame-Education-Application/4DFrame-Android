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

    fun uploadImages(
        context: Context,
        photoUris: List<Uri>,
        onSuccess: (String) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        val fileParts = photoUris.mapIndexed { index, uri ->
            val file = File(getRealPathFromURI(context, uri))
            val requestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val partName = when (index) {
                0 -> "frontImage"
                1 -> "backImage"
                2 -> "leftImage"
                3 -> "rightImage"
                4 -> "upImage"
                else -> "file$index"
            }
            MultipartBody.Part.createFormData(partName, file.name, requestBody)
        }

        if (fileParts.size >= 5) {
            apiService.uploadMultipleImages(
                fileParts[0], fileParts[1], fileParts[2], fileParts[3], fileParts[4]
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
