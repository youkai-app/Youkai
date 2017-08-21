package app.youkai.data.service

import app.youkai.data.models.*
import app.youkai.data.remote.Client
import app.youkai.util.SerializationUtils
import app.youkai.util.ext.append
import app.youkai.util.ext.capitalizeFirstLetter
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
        val converterFactory = JSONAPIConverterFactory(ResourceConverters.mainConverter)
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
    fun login(username: String, password: String) = service.postLogin(username, password, "password", CLIENT_ID, CLIENT_SECRET)

    fun refreshAuthToken(refreshToken: String) = service.refreshAuthToken(refreshToken, "refresh_token", CLIENT_ID, CLIENT_SECRET)

    private fun createAuthorizationParam(tokenType: String, authToken: String): String
            = tokenType.capitalizeFirstLetter().append(authToken, " ")

    /**
     * Anime
     */
    private val getAllAnimeCall = {
        _: String, h: Map<String, String>, m: Map<String, String> ->
        service.getAllAnime(m)
    }

    fun allAnime(): RequestBuilder<Observable<JSONAPIDocument<List<Anime>>>>
            = RequestBuilder("", getAllAnimeCall)

    private val getAnimeCall = {
        id: String, h: Map<String, String>, m: Map<String, String> ->
        service.getAnime(id, h, m)
    }

    fun anime(id: String): RequestBuilder<Observable<JSONAPIDocument<Anime>>>
            = RequestBuilder(id, getAnimeCall)

    fun searchAnime(query: String): RequestBuilder<Observable<JSONAPIDocument<List<Anime>>>>
            = RequestBuilder("", getAllAnimeCall).filter("text", query)

    private val getAnimeLanguagesCall = {
        id: String, h: Map<String, String>, m: Map<String, String> ->
        service.getAnimeLanguages(id, h, m)
    }

    fun languagesForAnime(animeId: String): RequestBuilder<Observable<List<String>>>
            = RequestBuilder(animeId, getAnimeLanguagesCall)

    private val getAnimeEpisodesCall = {
        id: String, h: Map<String, String>, m: Map<String, String> ->
        service.getAnimeEpisodes(id, h, m)
    }

    fun episodesForAnime(animeId: String): RequestBuilder<Observable<JSONAPIDocument<List<Episode>>>>
            = RequestBuilder(animeId, getAnimeEpisodesCall)

    /**
     * Manga
     */
    private val getAllMangaCall = {
        _: String, h: Map<String, String>, m: Map<String, String> ->
        service.getAllManga(m)
    }

    fun allManga(): RequestBuilder<Observable<JSONAPIDocument<List<Manga>>>>
            = RequestBuilder("", getAllMangaCall)

    private val getMangaCall = {
        id: String, h: Map<String, String>, m: Map<String, String> ->
        service.getManga(id, h, m)
    }

    fun manga(id: String): RequestBuilder<Observable<JSONAPIDocument<Manga>>>
            = RequestBuilder(id, getMangaCall)

    fun searchManga(query: String): RequestBuilder<Observable<JSONAPIDocument<List<Manga>>>>
            = RequestBuilder("", getAllMangaCall).filter("text", query)

    private val getMangaChaptersCall = {
        id: String, h: Map<String, String>, m: Map<String, String> ->
        service.getMangaChapters(id, h, m)
    }

    fun chaptersForManga(mangaId: String): RequestBuilder<Observable<JSONAPIDocument<List<Chapter>>>>
            = RequestBuilder(mangaId, getMangaChaptersCall)

    /**
     * Library
     */
    private val getAllLibraryEntriesCall = {
        _: String, h: Map<String, String>, m: Map<String, String> ->
        service.getAllLibraryEntries(m)
    }

    fun allLibraryEntries(): RequestBuilder<Observable<JSONAPIDocument<List<LibraryEntry>>>>
            = RequestBuilder("", getAllLibraryEntriesCall)

    private val getLibraryCall = {
        id: String, h: Map<String, String>, m: Map<String, String> ->
        service.getLibrary(id, h, m)
    }

    fun library(id: String): RequestBuilder<Observable<JSONAPIDocument<List<LibraryEntry>>>>
            = RequestBuilder(id, getLibraryCall)

    private val getLibraryEntryCall = {
        id: String, h: Map<String, String>, m: Map<String, String> ->
        service.getLibraryEntry(id, h, m)
    }

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
                .mainConverter
                .writeDocument(JSONAPIDocument<LibraryEntry>(libraryEntry))
                .toString(Charsets.UTF_8)

        // remove the ID as it should not be sent (the resource has not been create yet so throws an error on the server)
        body = SerializationUtils.removeFirstIdFromJson(body)

        return service.createLibraryEntry(
                createAuthorizationParam(tokenType, authToken),
                RequestBody.create(MediaType.parse(JSON_API_CONTENT_TYPE), body)
        )
    }

    fun updateLibraryEntry(libraryEntry: LibraryEntry, authToken: String, tokenType: String = "bearer"): Observable<JSONAPIDocument<LibraryEntry>> {
        val body = ResourceConverters.mainConverter.writeDocument(JSONAPIDocument<LibraryEntry>(libraryEntry))

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
    private val getAllFavoritesCall = {
        _: String, h: Map<String, String>, m: Map<String, String> ->
        service.getAllFavorites(h, m)
    }

    fun allFavorites(): RequestBuilder<Observable<JSONAPIDocument<List<Favorite>>>>
            = RequestBuilder("", getAllFavoritesCall)

    private val getFavoriteCall = {
        id: String, h: Map<String, String>, m: Map<String, String> ->
        service.getFavorite(id, h, m)
    }

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

    // TODO: create version of method without need for full object
    fun createFavorite(favorite: Favorite, authToken: String, tokenType: String = "bearer"): Observable<JSONAPIDocument<Favorite>> {
        favorite.id = "temporary_id"

        var body = ResourceConverters
                .mainConverter
                .writeDocument(JSONAPIDocument<Favorite>(favorite))
                .toString(Charsets.UTF_8)

        body = SerializationUtils.removeIdFromJson(body, favorite.id!!)

        return service.postFavorite(
                createAuthorizationParam(tokenType, authToken),
                RequestBody.create(MediaType.parse(JSON_API_CONTENT_TYPE), body)
        )
    }

    fun createFavorite(userId: String, mediaId: String, mediaType: JsonType, authToken: String, tokenType: String = "bearer") {
        //TODO: write (blocked by polymorph)
    }

    //TODO: test (need to make a test for [createFavorite] first so can test safely
    fun deleteFavorite(id: String): Observable<Response<Void>> {
        return service.deleteFavorite(id)
    }

    /**
     * Characters
     */
    private val getAllCharactersCall = {
        _: String, h: Map<String, String>, m: Map<String, String> ->
        service.getAllCharacters(h, m)
    }

    fun allCharacters(): RequestBuilder<Observable<JSONAPIDocument<List<Character>>>>
            = RequestBuilder("", getAllCharactersCall)

    private val getCharacterCall = {
        id: String, h: Map<String, String>, m: Map<String, String> ->
        service.getCharacter(id, h, m)
    }

    fun character(id: String): RequestBuilder<Observable<JSONAPIDocument<Character>>>
            = RequestBuilder(id, getCharacterCall)

    private val getAnimeCharactersCall = {
        id: String, h: Map<String, String>, m: Map<String, String> ->
        service.getAnimeCharacters(id, h, m)
    }

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
    private val getAllCastingsCall = {
        _: String, h: Map<String, String>, m: Map<String, String> ->
        service.getAllCastings(h, m)
    }

    fun allCastings(): RequestBuilder<Observable<JSONAPIDocument<List<Casting>>>>
            = RequestBuilder("", getAllCastingsCall)

    private val getCastingCall = {
        id: String, h: Map<String, String>, m: Map<String, String> ->
        service.getCasting(id, h, m)
    }

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


    /**
     * Reactions
     */
    private val getAllReactionsCall = {
        _: String, h: Map<String, String>, m: Map<String, String> ->
        service.getAllReactions(h, m)
    }

    fun allReactions(): RequestBuilder<Observable<JSONAPIDocument<List<Reaction>>>>
            = RequestBuilder("", getAllReactionsCall)

    private val getReactionCall = {
        id: String, h: Map<String, String>, m: Map<String, String> ->
        service.getReaction(id, h, m)
    }

    fun reaction(id: String): RequestBuilder<Observable<JSONAPIDocument<Reaction>>>
            = RequestBuilder(id, getReactionCall)

    private fun reactionsForMedia(mediaId: String, mediaType: String): RequestBuilder<Observable<JSONAPIDocument<List<Reaction>>>>
            = RequestBuilder("", getAllReactionsCall)
            .filter(mediaType + "Id", mediaId)

    fun reactionsForAnime(animeId: String): RequestBuilder<Observable<JSONAPIDocument<List<Reaction>>>>
            = reactionsForMedia(animeId, "anime")

    fun reactionsForManga(mangaId: String): RequestBuilder<Observable<JSONAPIDocument<List<Reaction>>>>
            = reactionsForMedia(mangaId, "manga")

    private val getLibraryEntryReactionCall = {
        id: String, h: Map<String, String>, m: Map<String, String> ->
        service.getLibraryEntryReaction(id, h, m)
    }

    fun reactionForLibraryEntry(libraryEntryId: String): RequestBuilder<Observable<JSONAPIDocument<Reaction>>>
            = RequestBuilder(libraryEntryId, getLibraryEntryReactionCall)

    /**
     * Users
     */
    private val getAllUsersCall = {
        _: String, h: Map<String, String>, m: Map<String, String> ->
        service.getAllUsers(h, m)
    }

    fun allUsers(): RequestBuilder<Observable<JSONAPIDocument<List<User>>>>
            = RequestBuilder("", getAllUsersCall)

    private val getUserCall = {
        id: String, h: Map<String, String>, m: Map<String, String> ->
        service.getUser(id, h, m)
    }

    fun user(id: String): RequestBuilder<Observable<JSONAPIDocument<User>>>
            = RequestBuilder(id, getUserCall)
}