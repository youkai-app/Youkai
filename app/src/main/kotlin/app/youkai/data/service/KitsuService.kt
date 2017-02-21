package app.youkai.data.service

import app.youkai.data.models.Anime
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface KitsuService {

    /** all headers must contain
     * Accept: application/vnd.api+json
     * Content-Type: application/vnd.api+json
     */

    @GET("anime/{id}")
    fun getAnime(@Path("id") id: String): Observable<Anime>

}