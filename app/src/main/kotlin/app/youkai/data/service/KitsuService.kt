package app.youkai.data.service

import app.youkai.data.models.Anime
import io.reactivex.Observable
import retrofit2.http.*

interface KitsuService {

    @GET("anime/{id}")
    fun getAnime(@Path("id") id: String): Observable<Anime>

}