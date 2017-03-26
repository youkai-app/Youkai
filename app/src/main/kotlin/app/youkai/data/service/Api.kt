package app.youkai.data.service

import app.youkai.data.models.Anime
import app.youkai.data.models.Manga
import app.youkai.data.remote.Client
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.jasminb.jsonapi.retrofit.JSONAPIConverterFactory
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object Api {

    private val BASE = "https://kitsu.io/api/"
    private val VERSION = "edge/"
    private val CLIENT_ID = "dd031b32d2f56c990b1425efe6c42ad847e7fe3ab46bf1299f05ecd856bdb7dd"
    private val CLIENT_SECRET = "54d7307928f63414defd96399fc31ba847961ceaecef3a5fd93144e960c0e151"

    /*
     * Lazy modifier only instantiates when the value is first used.
     * See: https://kotlinlang.org/docs/reference/delegated-properties.html
     */
    private val service: Service by lazy {
        val retrofit: Retrofit = Retrofit.Builder()
                .client(Client)
                .baseUrl(BASE + VERSION)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JSONAPIConverterFactory(ObjectMapper(), Anime::class.java))
                .build()

        retrofit.create(Service::class.java)
    }

    private val loginService: LoginService by lazy {
        val retrofit: Retrofit = Retrofit.Builder()
                .client(Client)
                .baseUrl(BASE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build()

        retrofit.create(LoginService::class.java)
    }

    fun login (username: String, password: String) = loginService.login(username, password, "password", CLIENT_ID, CLIENT_SECRET)

    fun refreshAuthToken (refreshToken: String) = loginService.refreshAuthToken(refreshToken, "refresh_token", CLIENT_ID, CLIENT_SECRET)

    fun anime(id: String): RequestBuilder<Observable<Anime>> {
        return RequestBuilder<Observable<Anime>>(id, { id: String, m: Map<String, String> -> service.getAnime(id, m) })
    }

    fun manga(id: String): RequestBuilder<Observable<Manga>> {
        return RequestBuilder<Observable<Manga>>(id, { id: String, m: Map<String, String> -> service.getManga(id, m) })
    }

}