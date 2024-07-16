//import com.blockmaker.fdland.data.source.remote.construct.ConstructDataSource

//package com.blockmaker.fdland.data.source.remote.construct
//
//import com.blockmaker.fdland.data.api.ConstructRetrofitInterface
//import com.blockmaker.fdland.data.model.ConstructImgResponse
//import com.greenfriends.zeroway.data.api.RetrofitClient
//import okhttp3.MultipartBody
//import retrofit2.Response
//
//class ConstructDataSourceImpl : ConstructDataSource {
//
//    private val ConstructService =
//        RetrofitClient.getRetrofit()?.create(ConstructRetrofitInterface::class.java)
//    override suspend fun setConstImg(
//        image_url: MultipartBody.Part
//    ): Response<Void> {
//        return ConstructService!!.setConstImg(image_url)
//    }
//
//    override suspend fun getConstImg(
//        image_url: String
//    ): Response<ConstructImgResponse> {
//        return ConstructService!!.getConstImg(image_url)
//    }
//}

//ConstructDataSourceImpl는 ConstructRetrofitInterface를 이용하여 이미지를 서버로 전송 API호출만 담당
package com.blockmaker.fdland.data.source.remote.construct

import com.blockmaker.fdland.data.api.ConstructRetrofitInterface
import com.blockmaker.fdland.data.api.RetrofitClient
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response

class ConstructDataSourceImpl : ConstructDataSource {
    // lazy 초기화를 사용하여 constructService를 초기화합니다.
    private val constructService: ConstructRetrofitInterface by lazy {
        RetrofitClient.getRetrofit().create(ConstructRetrofitInterface::class.java)
    }

    override suspend fun setConstImg(imgUrl: MultipartBody.Part): Response<ResponseBody> {
        return constructService.setConstImg(imgUrl)
    }
}
