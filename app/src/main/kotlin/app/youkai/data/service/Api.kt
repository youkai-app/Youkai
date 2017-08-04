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
    private val JSON_API_CONTENT_TYPE = "application/vnd.api+json"

    val BASE = "https://kitsu.io/api/"
    private val CLIENT_ID = "dd031b32d2f56c990b1425efe6c42ad847e7fe3ab46bf1299f05ecd856bdb7dd"
    private val CLIENT_SECRET = "54d7307928f63414defd96399fc31ba847961ceaecef3a5fd93144e960c0e151"

    /**
     * Lazy modifier only instantiates when the value is first used.
     * See: https://kotlinlang.org/docs/reference/delegated-properties.html
     */
    private val converterFactory by lazy {
        /**
         * Don't have to list all the classes manually, but we're doing so to prevent errors due to missing them.
         * Hopefully some diligence will keep this list updated.
         */
        val converterFactory = JSONAPIConverterFactory(
                ObjectMapper(),
                Anime::class.java,
                AnimeCharacter::class.java,
                AnimeProduction::class.java,
                AnimeStaff::class.java,
                Casting::class.java,
                Character::class.java,
                Episode::class.java,
                Favorite::class.java,
                Franchise::class.java,
                Genre::class.java,
                Installment::class.java,
                LibraryEntry::class.java,
                Manga::class.java,
                Mapping::class.java,
                MediaRelationship::class.java,
                Person::class.java,
                Producer::class.java,
                Review::class.java,
                Streamer::class.java,
                StreamingLink::class.java,
                User::class.java
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

    /**
     * Authentication
     */
    fun login (username: String, password: String) = service.postLogin(username, password, "password", CLIENT_ID, CLIENT_SECRET)

    fun refreshAuthToken (refreshToken: String) = service.refreshAuthToken(refreshToken, "refresh_token", CLIENT_ID, CLIENT_SECRET)

    private fun createAuthorizationParam (tokenType: String, authToken: String): String
            = tokenType.capitalizeFirstLetter().append(authToken, " ")

    /**
     * Anime
     */
    private val getAllAnimeCall = { _: String, m: Map<String, String> -> service.getAllAnime(m) }

    fun allAnime(): RequestBuilder<Observable<JSONAPIDocument<List<Anime>>>>
            = RequestBuilder("", getAllAnimeCall)

    private val getAnimeCall = { id: String, m: Map<String, String> -> service.getAnime(id, m) }

    fun anime(id: String): RequestBuilder<Observable<JSONAPIDocument<Anime>>>
            = RequestBuilder(id, getAnimeCall)

    fun searchAnime(query: String): RequestBuilder<Observable<JSONAPIDocument<List<Anime>>>>
            = RequestBuilder("", getAllAnimeCall).filter("text", query)

    private val getAnimeLanguagesCall = { id: String, m: Map<String, String> -> service.getAnimeLanguages(id, m) }

    fun languagesForAnime(animeId: String): RequestBuilder<Observable<List<String>>>
            = RequestBuilder(animeId, getAnimeLanguagesCall)

    /**
     * Manga
     */
    private val getAllMangaCall = { _: String, m: Map<String, String> -> service.getAllManga(m) }

    fun allManga(): RequestBuilder<Observable<JSONAPIDocument<List<Manga>>>>
            = RequestBuilder("", getAllMangaCall)

    private val getMangaCall = { id: String, m: Map<String, String> -> service.getManga(id, m) }

    fun manga(id: String): RequestBuilder<Observable<JSONAPIDocument<Manga>>>
            = RequestBuilder(id, getMangaCall)

    fun searchManga(query: String): RequestBuilder<Observable<JSONAPIDocument<List<Manga>>>>
            = RequestBuilder("", getAllMangaCall).filter("text", query)

    /**
     * Library
     */
    private val getAllLibraryEntriesCall = { _: String, m: Map<String, String> -> service.getAllLibraryEntries(m) }

    fun allLibraryEntries(): RequestBuilder<Observable<JSONAPIDocument<List<LibraryEntry>>>>
            = RequestBuilder("", getAllLibraryEntriesCall)

    private val getLibraryCall = { id: String, m: Map<String, String> -> service.getLibrary(id, m) }

    fun library(id: String): RequestBuilder<Observable<JSONAPIDocument<List<LibraryEntry>>>>
            = RequestBuilder(id, getLibraryCall)

    private val getLibraryEntryCall = { id: String, m: Map<String, String> -> service.getLibraryEntry(id, m) }

    fun libraryEntry(id: String): RequestBuilder<Observable<JSONAPIDocument<LibraryEntry>>>
            = RequestBuilder(id, getLibraryEntryCall)

    private fun libraryEntryForMedia(userId: String, mediaId: String, mediaType: String)
            : RequestBuilder<Observable<JSONAPIDocument<List<LibraryEntry>>>> {
        return RequestBuilder("", getAllLibraryEntriesCall)
                .filter("userId", userId)
                .filter("kind", mediaType)
                .filter(mediaType + "Id", mediaId)
                .page("limit", 1)
    }

    fun libraryEntryForAnime(userId: String, animeId: String): RequestBuilder<Observable<JSONAPIDocument<List<LibraryEntry>>>>
            = libraryEntryForMedia(userId, animeId, "anime")

    fun libraryEntryForManga(userId: String, mangaId: String): RequestBuilder<Observable<JSONAPIDocument<List<LibraryEntry>>>>
            = libraryEntryForMedia(userId, mangaId, "manga")

    fun createLibraryEntry(libraryEntry: LibraryEntry, authToken: String, tokenType: String = "bearer")
            : Observable<Response<ResponseBody>> {
        libraryEntry.id = "temporary_id"

        var body: String = ResourceConverters
                .libraryEntryConverter
                .writeDocument(JSONAPIDocument<LibraryEntry>(libraryEntry))
                .toString(Charsets.UTF_8)

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

    /**
     * Favorites
     */
    private val getAllFavoritesCall = { _: String, m: Map<String, String> -> service.getAllFavorites(m) }

    fun allFavorites(): RequestBuilder<Observable<JSONAPIDocument<List<Favorite>>>>
            = RequestBuilder("", getAllFavoritesCall)

    private val getFavoriteCall = { id: String, m: Map<String, String> -> service.getFavorite(id, m) }

    fun favorite(id: String): RequestBuilder<Observable<JSONAPIDocument<Favorite>>>
            = RequestBuilder(id, getFavoriteCall)

    private fun favoriteForMedia(userId: String, mediaId: String, mediaType: String)
            : RequestBuilder<Observable<JSONAPIDocument<List<Favorite>>>> {
        return RequestBuilder("", getAllFavoritesCall)
                .filter("userId", userId)
                .filter("itemId", mediaId)
                .filter("itemType", mediaType)
                .page("limit", 1)
    }

    fun favoriteForAnime(userId: String, animeId: String): RequestBuilder<Observable<JSONAPIDocument<List<Favorite>>>>
            = favoriteForMedia(userId, animeId, "Anime")

    fun favoriteForManga(userId: String, mangaId: String): RequestBuilder<Observable<JSONAPIDocument<List<Favorite>>>>
            = favoriteForMedia(userId, mangaId, "Manga")

    /**
     * Characters
     */
    private val getAllCharactersCall = { _: String, m: Map<String, String> -> service.getAllCharacters(m) }

    fun allCharacters(): RequestBuilder<Observable<JSONAPIDocument<List<Character>>>>
            = RequestBuilder("", getAllCharactersCall)

    private val getCharacterCall = { id: String, m: Map<String, String> -> service.getCharacter(id, m) }

    fun character(id: String): RequestBuilder<Observable<JSONAPIDocument<Character>>>
            = RequestBuilder(id, getCharacterCall)

    private val getAnimeCharactersCall = { id: String, m: Map<String, String> -> service.getAnimeCharacters(id, m) }

    /**
     * No equivalent method for Manga due to current Api limits (no filter for `mediaType` on `/characters` and `manga-characters` is unfilled.
     * You must use [castingsForManga] and append the following [RequestBuilder] methods:
     *          .filter("isCharacter", "true")
     *          .include("character", "person")
     */
    fun charactersForAnime(animeId: String): RequestBuilder<Observable<JSONAPIDocument<List<AnimeCharacter>>>>
            = RequestBuilder(animeId, getAnimeCharactersCall)

    /**
     * Castings
     */
    private val getAllCastingsCall = { _: String, m: Map<String, String> -> service.getAllCastings(m) }

    fun allCastings(): RequestBuilder<Observable<JSONAPIDocument<List<Casting>>>>
            = RequestBuilder("", getAllCastingsCall)

    private val getCastingCall = { id: String, m: Map<String, String> -> service.getCasting(id, m) }

    fun casting(id: String): RequestBuilder<Observable<JSONAPIDocument<Casting>>>
            = RequestBuilder(id, getCastingCall)

    private fun castingsForMedia(mediaId: String, mediaType: String): RequestBuilder<Observable<JSONAPIDocument<List<Casting>>>>
            = RequestBuilder("", getAllCastingsCall)
            .filter("mediaType", mediaType)
            .filter("mediaId", mediaId)

    fun castingsForAnime(animeId: String): RequestBuilder<Observable<JSONAPIDocument<List<Casting>>>>
            = castingsForMedia(animeId, "Anime")

    fun castingsForManga(mangaId: String): RequestBuilder<Observable<JSONAPIDocument<List<Casting>>>>
            = castingsForMedia(mangaId, "Manga")

}