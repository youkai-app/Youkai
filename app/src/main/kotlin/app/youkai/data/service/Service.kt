package app.youkai.data.service

import app.youkai.data.models.Anime
import app.youkai.data.models.Manga
import io.reactivex.Observable
import retrofit2.http.*

interface Service {

    @GET("anime/{id}")
    fun getAnime(@Path("id") id: String,
                 @QueryMap queries: Map<String, String>): Observable<Anime>

    @GET("manga/{id}")
    fun getManga(@Path("id") id: String,
                 @QueryMap queries: Map<String, String>): Observable<Manga>

}