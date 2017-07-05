package app.youkai.data.service

import app.youkai.data.models.*
import com.github.jasminb.jsonapi.JSONAPIDocument
import io.reactivex.Observable
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
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
    fun getAnime(
            @Path("id") id: String,
            @QueryMap queries: Map<String, String>
    ): Observable<JSONAPIDocument<Anime>>

    @GET("edge/manga/{id}")
    fun getManga(
            @Path("id") id: String,
            @QueryMap queries: Map<String, String>
    ): Observable<JSONAPIDocument<Manga>>

    @GET("edge/users/{id}/library-entries")
    fun getLibrary(
            @Path("id") id: String,
            @QueryMap queries: Map<String, String>
    ): Observable<JSONAPIDocument<List<LibraryEntry>>>

    @GET("edge/library-entries/{id}")
    fun getLibraryEntry(
            @Path("id") id: String,
            @QueryMap queries: Map<String, String>
    ): Observable<JSONAPIDocument<LibraryEntry>>

    @POST("edge/library-entries")
    @Headers("Content-Type: application/vnd.api+json")
    fun createLibraryEntry(
            @Header("Authorization") authorization: String,
            @Body body: RequestBody
    ): Observable<Response<ResponseBody>>

    @PATCH("edge/library-entries/{id}")
    @Headers("Content-Type: application/vnd.api+json")
    fun updateLibraryEntry(
            @Header("Authorization") authorization: String,
            @Path("id") id: String,
            @Body body: RequestBody
    ): Observable<JSONAPIDocument<LibraryEntry>>

    @DELETE("edge/library-entries/{id}")
    @Headers("Content-Type: application/vnd.api+json")
    fun deleteLibraryEntry(
            @Header("Authorization") authorization: String,
            @Path("id") id: String
    ): Observable<Response<Void>>

    @GET("edge/anime")
    fun  searchAnime(@QueryMap queries: Map<String, String>): Observable<JSONAPIDocument<List<Anime>>>

    @GET("edge/manga")
    fun  searchManga(@QueryMap queries: Map<String, String>): Observable<JSONAPIDocument<List<Manga>>>

}