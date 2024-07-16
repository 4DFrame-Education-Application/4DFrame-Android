//package com.blockmaker.fdland.data.repository
//
//import com.blockmaker.fdland.data.source.remote.construct.ConstructDataSourceImpl
//import okhttp3.MultipartBody
//import retrofit2.Response
//
//class ConstRepository(private val constructDataSourceImpl: ConstructDataSourceImpl) {
//
//    suspend fun setConstImg(
//        image_url: MultipartBody.Part
//    ): Response<Void> { 결과값 json으로 받기에 voide가 아닌 <responsebody>사용
//        return constructDataSourceImpl.setConstImg(image_url)
//    }
//}
package com.blockmaker.fdland.data.Repository

import android.content.Context
import android.net.Uri
import com.blockmaker.fdland.data.source.remote.construct.ConstructDataSource
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response

class ConstRepository(private val constructDataSource: ConstructDataSource) {
    suspend fun setConstImg(imgUrl: MultipartBody.Part): Response<ResponseBody> {
        return constructDataSource.setConstImg(imgUrl)
    }
}
