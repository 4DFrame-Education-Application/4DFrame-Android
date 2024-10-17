import com.blockmaker.fdland.data.model.AuthResponse
import com.blockmaker.fdland.data.model.SendEmailResponse
import com.blockmaker.fdland.data.model.SendVerifiedResponse
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthRetrofitInterface {
    @POST("api/sign/sign-in")
    fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): Call<AuthResponse>

    @POST("/api/sign/send-mail")
    fun sendEmail(
        @Query("email") email: String
    ): Call<SendEmailResponse>

    @POST("/api/sign/verified")
    fun sendVerified(
        @Query("confirmationCode") confirmationCode: String
    ): Call<SendVerifiedResponse>
}