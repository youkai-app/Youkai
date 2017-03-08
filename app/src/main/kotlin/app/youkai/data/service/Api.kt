package app.youkai.data.service

import app.youkai.data.models.Anime
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.jasminb.jsonapi.retrofit.JSONAPIConverterFactory
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object Api {

    private val BASE: String = "https://kitsu.io/api/"
    private val VERSION: String = "edge/"

    /*
     * Lazy modifier only instantiates when the value is first used.
     * See: https://kotlinlang.org/docs/reference/delegated-properties.html
     */
    val service: Service by lazy {

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BASE + VERSION)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JSONAPIConverterFactory(ObjectMapper(), Anime::class.java))
                .build()

        retrofit.create(Service::class.java)

    }

    val loginService: LoginService by lazy {

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BASE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build()

        retrofit.create(LoginService::class.java)

    }

    fun login (username: String, password: String) = loginService.login(username, password, "password")

    fun getAnime(id: String) = service.getAnime(id)

    fun getManga(id: String) = service.getManga(id)

}