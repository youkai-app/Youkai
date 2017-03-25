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

    private val BASE: String = "https://kitsu.io/api/"
    private val VERSION: String = "edge/"

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

    fun login (username: String, password: String) = loginService.login(username, password, "password")

    fun anime(id: String): RequestBuilder<Observable<Anime>> {
        return RequestBuilder<Observable<Anime>>(id, { id: String, m: Map<String, String> -> service.getAnime(id, m) })
    }

    fun manga(id: String): RequestBuilder<Observable<Manga>> {
        return RequestBuilder<Observable<Manga>>(id, { id: String, m: Map<String, String> -> service.getManga(id, m) })
    }

}