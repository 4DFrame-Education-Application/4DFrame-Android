import com.blockmaker.fdland.data.model.AuthResponse
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthRetrofitInterface {
    @POST("api/sign/sign-in")
    fun login(
        @Query("email") email: String,  // Query 파라미터로 이메일 추가
        @Query("password") password: String  // Query 파라미터로 패스워드 추가
    ): Call<AuthResponse>
}