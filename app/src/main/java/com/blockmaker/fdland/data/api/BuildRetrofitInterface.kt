//package com.blockmaker.fdland.data.api
//
//import com.blockmaker.fdland.data.model.BuildResultResponse
//import retrofit2.Response
//import retrofit2.http.GET
//import retrofit2.http.Multipart
//import retrofit2.http.POST
//import retrofit2.http.Part
//import retrofit2.http.Query
//
//interface BuildRetrofitInterface {
//
//    /** 카메라 페이지: 이미지 보내기 **/
//    @Multipart
//    @POST("results/upload")
//    suspend fun setImageupload(
//        @Part imageUrl: String
//    ): Response<Void>
//
//    /** 카메라 페이지: 이미지 받기 **/
//    @GET("results/upload")
//    suspend fun getImageupload(
//        @Query("accuracy") accuracy: String?,
//        @Query("imageUrl") imageUrl: String?,
//        @Query("name") name: String?,
//        @Query("rate") rate: String?
//    ): Response<BuildResultResponse>
//
//}
package com.blockmaker.fdland.data.api

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface BuildRetrofitInterface {

    /** 카메라 페이지: 이미지 보내기 **/
    @Multipart
    @POST("api/block/upload")
    fun uploadMultipleImages(
        @Part frontImage: MultipartBody.Part,
        @Part backImage: MultipartBody.Part,
        @Part leftImage: MultipartBody.Part,
        @Part rightImage: MultipartBody.Part,
        @Part upImage: MultipartBody.Part
    ): Call<ResponseBody>


}