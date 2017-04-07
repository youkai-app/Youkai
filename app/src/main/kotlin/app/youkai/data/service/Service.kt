package app.youkai.data.service

import app.youkai.data.models.Anime
import app.youkai.data.models.Manga
import com.github.jasminb.jsonapi.JSONAPIDocument
import io.reactivex.Observable
import retrofit2.http.*

interface Service {

    @GET("anime/{id}")
    fun getAnime(@Path("id") id: String,
                 @QueryMap queries: Map<String, String>): Observable<JSONAPIDocument<Anime>>

    @GET("manga/{id}")
    fun getManga(@Path("id") id: String,
                 @QueryMap queries: Map<String, String>): Observable<JSONAPIDocument<Manga>>

}