package app.youkai.data.service

import app.youkai.data.models.Anime
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.jasminb.jsonapi.retrofit.JSONAPIConverterFactory
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object KitsuApi {

    private val BASE: String = "https://kitsu.io/api/"
    private val VERSION: String = "edge/"

    private var service: KitsuService

    init {

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BASE + VERSION)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JSONAPIConverterFactory(ObjectMapper(), Anime::class.java))
                .build()

        service = retrofit.create(KitsuService::class.java)

    }

    val loginService: LoginService by lazy {

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BASE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build()

        retrofit.create(LoginService::class.java)

    }

    fun getAnime(id: String) : Observable<Anime> = service.getAnime(id)

    fun login (username: String, password: String) = loginService.login(username, password, "password")

}