import com.blockmaker.fdland.data.model.MyPageResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface MyPageRetrofitInterface {

    @GET("/api/user/get-user")
    fun getMyPageInfo(
        @Header("X-AUTH-TOKEN") token: String
    ): Call<MyPageResponse>
}