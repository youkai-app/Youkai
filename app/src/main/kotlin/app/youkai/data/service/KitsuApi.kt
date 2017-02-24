package app.youkai.data.service

import app.youkai.data.models.Anime
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.jasminb.jsonapi.retrofit.JSONAPIConverterFactory
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import retrofit2.Retrofit

object KitsuApi {

    private val BASE: String = "https://kitsu.io/api/edge/"

    private var service: KitsuService

    init {

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BASE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JSONAPIConverterFactory(ObjectMapper(), Anime::class.java))
                .build()

        service = retrofit.create(KitsuService::class.java)
    }

    fun getAnime(id: String) : Observable<Anime> {

        return service.getAnime(id)

    }

}