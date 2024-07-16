//package com.blockmaker.fdland.data.source.remote.construct
//
//import com.blockmaker.fdland.data.model.ConstructImgResponse
//import okhttp3.MultipartBody
//import retrofit2.Response
//
//interface ConstructDataSource {
//
//    suspend fun setConstImg(
//        image_url: MultipartBody.Part
//    ): Response<Void>
//
//    suspend fun getConstImg(
//        image_url: String
//    ): Response<ConstructImgResponse>
//}

package com.blockmaker.fdland.data.source.remote.construct

import android.content.Context
import android.net.Uri
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response

interface ConstructDataSource {
    suspend fun setConstImg(imgUrl: MultipartBody.Part): Response<ResponseBody>
}
