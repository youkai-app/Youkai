package app.youkai.data.service

import app.youkai.data.models.*
import app.youkai.data.remote.Client
import app.youkai.util.ext.append
import app.youkai.util.ext.capitalizeFirstLetter
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.jasminb.jsonapi.JSONAPIDocument
import com.github.jasminb.jsonapi.retrofit.JSONAPIConverterFactory
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object Api {
    val JSON_API_CONTENT_TYPE = "application/vnd.api+json"

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
        return RequestBuilder<Observable<JSONAPIDocument<Anime>>>(id.toString(), getAnimeCall)
    }

    private val getMangaCall = { id: String, m: Map<String, String> -> service.getManga(id, m) }

    fun manga(id: String): RequestBuilder<Observable<JSONAPIDocument<Manga>>> {
        return RequestBuilder<Observable<JSONAPIDocument<Manga>>>(id, getMangaCall)
    }

    private val getLibraryCall = { id: String, m: Map<String, String> -> service.getLibrary(id, m) }

    fun library(id: String): RequestBuilder<Observable<JSONAPIDocument<List<LibraryEntry>>>> {
        return RequestBuilder<Observable<JSONAPIDocument<List<LibraryEntry>>>>(id, getLibraryCall)
    }

    private val getLibraryEntryCall = { id: String, m: Map<String, String> -> service.getLibraryEntry(id, m) }

    fun libraryEntry(id: String): RequestBuilder<Observable<JSONAPIDocument<LibraryEntry>>> {
        return RequestBuilder<Observable<JSONAPIDocument<LibraryEntry>>>(id, getLibraryEntryCall)
    }

    fun createLibraryEntry(libraryEntry: LibraryEntry, authToken: String, tokenType: String = "bearer"): Observable<ResponseBody> {
        libraryEntry.id = "temporary_id"

        var body: String = ResourceConverters.libraryEntryConverter.writeDocument(JSONAPIDocument<LibraryEntry>(libraryEntry)).toString(Charsets.UTF_8)

        /**
         * This removes the ID from the content request. Currently the jsonapi library does not allow
         * serialization of objects without IDs. TODO: Submit a PR to allow for this use case.
         */
        body = body.replace("\"id\":\"${libraryEntry.id}\",", "")

        return service.createLibraryEntry(
                createAuthorizationParam(tokenType, authToken),
                RequestBody.create(MediaType.parse(JSON_API_CONTENT_TYPE), body)
        )
    }

    fun updateLibraryEntry(libraryEntry: LibraryEntry, authToken: String, tokenType: String = "bearer"): Observable<JSONAPIDocument<LibraryEntry>> {
        val body = ResourceConverters.libraryEntryConverter.writeDocument(JSONAPIDocument<LibraryEntry>(libraryEntry))

        return service.updateLibraryEntry(
                createAuthorizationParam(tokenType, authToken),
                libraryEntry.id!!,
                RequestBody.create(MediaType.parse(JSON_API_CONTENT_TYPE), body)
        )
    }

    fun deleteLibraryEntry(id: String, authToken: String, tokenType: String = "bearer"): Observable<Response<Void>>
            = service.deleteLibraryEntry(createAuthorizationParam(tokenType, authToken), id)

    fun createAuthorizationParam (tokenType: String, authToken: String): String {
        return tokenType.capitalizeFirstLetter().append(authToken, " ")
    }

    private val searchAnimeCall = { ignored: String, m: Map<String, String> -> service.searchAnime(m) }

    fun searchAnime(query: String): RequestBuilder<Observable<JSONAPIDocument<List<Anime>>>> {
        return RequestBuilder<Observable<JSONAPIDocument<List<Anime>>>>("", searchAnimeCall)
                .filter("text", query)
    }

    private val searchMangaCall = { ignored: String, m: Map<String, String> -> service.searchManga(m) }

    fun searchManga(query: String): RequestBuilder<Observable<JSONAPIDocument<List<Manga>>>> {
        return RequestBuilder<Observable<JSONAPIDocument<List<Manga>>>>("", searchMangaCall)
                .filter("text", query)
    }

}