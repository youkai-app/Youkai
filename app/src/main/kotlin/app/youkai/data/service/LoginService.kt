package app.youkai.data.service

import app.youkai.data.models.Credentials
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginService {

    @POST("oauth/token")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    fun  login(@Field("username") username: String,
               @Field("password") password: String,
               @Field("grant_type") grantType: String) : Observable<Credentials>

}