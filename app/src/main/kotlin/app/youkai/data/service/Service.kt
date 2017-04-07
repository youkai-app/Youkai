package app.youkai.data.service

import app.youkai.data.models.*
import com.github.jasminb.jsonapi.JSONAPIDocument
import io.reactivex.Observable
import retrofit2.http.*

interface Service {
    /**
     * Authentication
     */
    @POST("oauth/token")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    fun login(
            @Field("username") username: String,
            @Field("password") password: String,
            @Field("grant_type") grantType: String,
            @Field("client_id") clientId: String,
            @Field("client_secret") clientSecret: String
    ): Observable<Credentials>

    @POST("oauth/token")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    fun refreshAuthToken(
            @Field("refresh_token") refreshToken: String,
            @Field("grant_type") grantType: String,
            @Field("client_id") clientId: String,
            @Field("client_secret") clientSecret: String
    ): Observable<Credentials>

    /**
     * Everything else.
     */
    @GET("edge/anime/{id}")
    fun getAnime(@Path("id") id: String,
                 @QueryMap queries: Map<String, String>): Observable<JSONAPIDocument<Anime>>

    @GET("edge/manga/{id}")
    fun getManga(@Path("id") id: String,
                 @QueryMap queries: Map<String, String>
    ): Observable<JSONAPIDocument<Manga>>

}