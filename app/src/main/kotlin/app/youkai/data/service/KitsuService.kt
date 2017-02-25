package app.youkai.data.service

import app.youkai.data.models.Anime
import app.youkai.data.models.Credentials
import io.reactivex.Observable
import retrofit2.http.*

interface KitsuService {

    /** all headers must contain
     * Accept: application/vnd.api+json
     * Content-Type: application/vnd.api+json
     */

    @GET("edge/anime/{id}")
    fun getAnime(@Path("id") id: String): Observable<Anime>

    @POST("oauth/token")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    fun  login(@Field("username") username: String, @Field("password") password: String, @Field("grant_type") grantType: String) : Observable<Credentials>

}