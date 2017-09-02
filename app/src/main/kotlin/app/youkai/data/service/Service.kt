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
    fun postLogin(
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
     * Anime
     */
    @GET("edge/anime")
    fun getAllAnime(@QueryMap queries: Map<String, String>): Observable<JSONAPIDocument<List<Anime>>>

    @GET("edge/anime/{id}")
    fun getAnime(
            @Path("id") id: String,
            @HeaderMap headers: Map<String, String>,
            @QueryMap queries: Map<String, String>
    ): Observable<JSONAPIDocument<Anime>>

    @GET("edge/anime/{id}/_languages")
    fun getAnimeLanguages(
            @Path("id") id: String,
            @HeaderMap headers: Map<String, String>,
            @QueryMap queries: Map<String, String>
    ): Observable<List<String>>

    @GET("edge/anime/{id}/anime-characters")
    fun getAnimeCharacters(
            @Path("id") id: String,
            @HeaderMap headers: Map<String, String>,
            @QueryMap queries: Map<String, String>
    ): Observable<JSONAPIDocument<List<AnimeCharacter>>>

    @GET("edge/anime/{id}/episodes")
    fun getAnimeEpisodes(
            @Path("id") id: String,
            @HeaderMap headers: Map<String, String>,
            @QueryMap queries: Map<String, String>
    ): Observable<JSONAPIDocument<List<Episode>>>

    @GET("edge/anime/{id}/media-relationships")
    fun getAnimeMediaRelationships(
            @Path("id") id: String,
            @HeaderMap headers: Map<String, String>,
            @QueryMap queries: Map<String, String>
    ): Observable<JSONAPIDocument<List<MediaRelationship>>>

    /**
     * Manga
     */
    @GET("edge/manga")
    fun getAllManga(@QueryMap queries: Map<String, String>): Observable<JSONAPIDocument<List<Manga>>>

    @GET("edge/manga/{id}")
    fun getManga(
            @Path("id") id: String,
            @HeaderMap headers: Map<String, String>,
            @QueryMap queries: Map<String, String>
    ): Observable<JSONAPIDocument<Manga>>

    @GET("edge/manga/{id}/chapters")
    fun getMangaChapters(
            @Path("id") id: String,
            @HeaderMap headers: Map<String, String>,
            @QueryMap queries: Map<String, String>
    ): Observable<JSONAPIDocument<List<Chapter>>>

    @GET("edge/manga/{id}/media-relationships")
    fun getMangaMediaRelationships(
            @Path("id") id: String,
            @HeaderMap headers: Map<String, String>,
            @QueryMap queries: Map<String, String>
    ): Observable<JSONAPIDocument<List<MediaRelationship>>>

    /**
     * Library
     */
    @GET("edge/library-entries")
    fun getAllLibraryEntries(@QueryMap queries: Map<String, String>): Observable<JSONAPIDocument<List<LibraryEntry>>>

    @GET("edge/users/{id}/library-entries")
    fun getLibrary(
            @Path("id") id: String,
            @HeaderMap headers: Map<String, String>,
            @QueryMap queries: Map<String, String>
    ): Observable<JSONAPIDocument<List<LibraryEntry>>>

    @GET("edge/library-entries/{id}")
    fun getLibraryEntry(
            @Path("id") id: String,
            @HeaderMap headers: Map<String, String>,
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

    /**
     * Favorites
     */
    @GET("edge/favorites")
    fun getAllFavorites(
            @HeaderMap headers: Map<String, String>,
            @QueryMap queries: Map<String, String>
    ): Observable<JSONAPIDocument<List<Favorite>>>

    @GET("edge/favorites/{id}")
    fun getFavorite(
            @Path("id") id: String,
            @HeaderMap headers: Map<String, String>,
            @QueryMap queries: Map<String, String>
    ): Observable<JSONAPIDocument<Favorite>>

    @POST("edge/favorites")
    @Headers("Content-Type: application/vnd.api+json")
    fun postFavorite(
            @Header("Authorization") authorization: String,
            @Body body: RequestBody
    ): Observable<JSONAPIDocument<Favorite>>

    @DELETE("edge/favorites/{id}")
    @Headers("Content-Type: application/vnd.api+json")
    fun deleteFavorite(
            @Path("id") id: String
    ): Observable<Response<Void>>

    /**
     * Characters
     */
    @GET("edge/characters")
    fun getAllCharacters(
            @HeaderMap headers: Map<String, String>,
            @QueryMap queries: Map<String, String>
    ): Observable<JSONAPIDocument<List<Character>>>

    @GET("edge/characters/{id}")
    fun getCharacter(
            @Path("id") id: String,
            @HeaderMap headers: Map<String, String>,
            @QueryMap queries: Map<String, String>
    ): Observable<JSONAPIDocument<Character>>

    /**
     * Castings
     */
    @GET("edge/castings")
    fun getAllCastings(
            @HeaderMap headers: Map<String, String>,
            @QueryMap queries: Map<String, String>
    ): Observable<JSONAPIDocument<List<Casting>>>

    @GET("edge/castings/{id}")
    fun getCasting(
            @Path("id") id: String,
            @HeaderMap headers: Map<String, String>,
            @QueryMap queries: Map<String, String>
    ): Observable<JSONAPIDocument<Casting>>

    /**
     * Reactions
     */
    @GET("edge/media-reactions")
    fun getAllReactions(
            @HeaderMap headers: Map<String, String>,
            @QueryMap queries: Map<String, String>
    ): Observable<JSONAPIDocument<List<Reaction>>>

    @GET("edge/media-reactions/{id}")
    fun getReaction(
            @Path("id") id: String,
            @HeaderMap headers: Map<String, String>,
            @QueryMap queries: Map<String, String>
    ): Observable<JSONAPIDocument<Reaction>>

    @GET("edge/library-entries/{id}/media-reaction")
    fun getLibraryEntryReaction(
            @Path("id") id: String,
            @HeaderMap headers: Map<String, String>,
            @QueryMap queries: Map<String, String>
    ): Observable<JSONAPIDocument<Reaction>>

    /**
     * Users
     */
    @GET("edge/users")
    fun getAllUsers(
            @HeaderMap headers: Map<String, String>,
            @QueryMap queries: Map<String, String>
    ): Observable<JSONAPIDocument<List<User>>>

    @GET("edge/users/{id}")
    fun getUser(
            @Path("id") id: String,
            @HeaderMap headers: Map<String, String>,
            @QueryMap queries: Map<String, String>
    ): Observable<JSONAPIDocument<User>>
}