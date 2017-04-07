package app.youkai.data.service

import app.youkai.data.models.*
import app.youkai.data.remote.Client
import app.youkai.util.ext.append
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.jasminb.jsonapi.JSONAPIDocument
import com.github.jasminb.jsonapi.retrofit.JSONAPIConverterFactory
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object Api {

    val BASE = "https://kitsu.io/api/"
    private val CLIENT_ID = "dd031b32d2f56c990b1425efe6c42ad847e7fe3ab46bf1299f05ecd856bdb7dd"
    private val CLIENT_SECRET = "54d7307928f63414defd96399fc31ba847961ceaecef3a5fd93144e960c0e151"

    /*
     * Lazy modifier only instantiates when the value is first used.
     * See: https://kotlinlang.org/docs/reference/delegated-properties.html
     */
    private val converterFactory by lazy {
        val converterFactory = JSONAPIConverterFactory(
                ObjectMapper(),
                Anime::class.java,
                Manga::class.java
        )
        converterFactory.setAlternativeFactory(JacksonConverterFactory.create())
        converterFactory
    }

    private val service: Service by lazy {
        val retrofit: Retrofit = Retrofit.Builder()
                .client(Client)
                .baseUrl(BASE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(converterFactory)
                .build()

        retrofit.create(Service::class.java)
    }

    fun login (username: String, password: String) = service.login(username, password, "password", CLIENT_ID, CLIENT_SECRET)

    fun refreshAuthToken (refreshToken: String) = service.refreshAuthToken(refreshToken, "refresh_token", CLIENT_ID, CLIENT_SECRET)

    private val getAnimeCall = { id: String, m: Map<String, String> -> service.getAnime(id, m) }

    fun anime(id: String): RequestBuilder<Observable<JSONAPIDocument<Anime>>> {
        return RequestBuilder<Observable<JSONAPIDocument<Anime>>>(id, getAnimeCall)
    }

    private val getMangaCall = { id: String, m: Map<String, String> -> service.getManga(id, m) }

    fun manga(id: String): RequestBuilder<Observable<JSONAPIDocument<Manga>>> {
        return RequestBuilder<Observable<JSONAPIDocument<Manga>>>(id, getMangaCall)
    }

}