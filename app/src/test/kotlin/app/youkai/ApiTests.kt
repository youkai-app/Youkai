package app.youkai

import app.youkai.data.models.*
import app.youkai.data.service.Api
import com.github.jasminb.jsonapi.JSONAPIDocument
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

/**
 * The tests need to be run in order as the server has validation checks on library entries (to prevent duplicates).
 * To get around this without relying on making library entries randomly the entry must first be deleted before a new one is created.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ApiTests {

    /**
     * Set your username and password for api tests. Remember to remove before committing anything.
     * Tests will pass if null because they aren't run.
     */
    val TEST_USERNAME: String? = "throwaway54321"
    val TEST_PASSWORD: String? = "xipsmistress"
    val TEST_ACCOUNT_REMOTE_USER_ID = "157458"

    /**
     * Anime
     */
    @Test
    @Throws(Exception::class)
    fun basicAnimeTest() {
        Api.anime("7442").get()
                .map(JSONAPIDocument<Anime>::get)
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertValue { a -> a.id.equals("7442") }
    }

    @Test
    @Throws(Exception::class)
    fun animeWithIncludesTest() {
        Api.anime("3919").include(BaseMedia.CASTINGS, Anime.EPISODES).get()
                .map(JSONAPIDocument<Anime>::get)
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertValue { a -> a.id.equals("3919") }
                .assertValue { a -> a.castings != null }
                .assertValue { a -> a.episodes != null }
    }

    @Test
    @Throws(Exception::class)
    fun fullAnimeTest() {
        Api.anime("1")
                .include(
                        BaseMedia.GENRES,
                        BaseMedia.CASTINGS,
                        BaseMedia.INSTALLMENTS,
                        BaseMedia.MAPPINGS,
                        BaseMedia.MEDIA_RELATIONSHIPS,
                        BaseMedia.REVIEWS,
                        Anime.EPISODES,
                        Anime.PRODUCTIONS,
                        Anime.CHARACTERS,
                        Anime.STAFF)
                .get()
                .map(JSONAPIDocument<Anime>::get)
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertValue { a -> a.genres != null }
                .assertValue { a -> a.castings != null }
                .assertValue { a -> a.installments != null }
                .assertValue { a -> a.mappings != null }
                .assertValue { a -> a.medias != null }
                .assertValue { a -> a.reviews != null }
                .assertValue { a -> a.episodes != null }
                .assertValue { a -> a.productions != null }
                .assertValue { a -> a.animeCharacters != null }
                .assertValue { a -> a.staff != null }
                .assertValue { a -> a.genreLinks != null }
                .assertValue { a -> a.castingLinks != null }
                .assertValue { a -> a.installmentLinks != null }
                .assertValue { a -> a.mappingLinks != null }
                .assertValue { a -> a.mediaLinks != null }
                .assertValue { a -> a.reviewLinks != null }
                .assertValue { a -> a.episodeLinks != null }
                .assertValue { a -> a.productionLinks != null }
                .assertValue { a -> a.animeCharacterLinks != null }
                .assertValue { a -> a.staffLinks != null }
    }

    @Test
    @Throws(Exception::class)
    fun searchAnimeTest() {
        Api.searchAnime("goddamnit").get()
                .map(JSONAPIDocument<List<Anime>>::get)
                .flatMapIterable { l -> l }
                .test()
                .assertNoErrors()
                .assertComplete()
    }

    @Test
    @Throws(Exception::class)
    fun allAnimeTest() {
        Api.allAnime().get()
                .map(JSONAPIDocument<List<Anime>>::get)
                .flatMapIterable { l -> l }
                .test()
                .assertNoErrors()
                .assertComplete()
    }

    @Test
    @Throws(Exception::class)
    fun animeLanguagesTest() {
        Api.animeLanguages("1").get()
                .flatMapIterable { l -> l }
                .doOnNext { s -> System.out.println(s) }
                .test()
                .assertNoErrors()
                .assertComplete()
    }

    @Test
    @Throws(Exception::class)
    fun animeCharactersTest() {
        Api.animeCharacters("1").get()
                .map(JSONAPIDocument<List<AnimeCharacter>>::get)
                .flatMapIterable { l -> l }
                .test()
                .assertNoErrors()
                .assertComplete()
    }

    /**
     * Manga
     */
    @Test
    @Throws(Exception::class)
    fun searchMangaTest() {
        Api.searchManga("goddamnit").get()
                .map(JSONAPIDocument<List<Manga>>::get)
                .flatMapIterable { l -> l }
                .test()
                .assertNoErrors()
                .assertComplete()
    }

    @Test
    @Throws(Exception::class)
    fun allMangaTest() {
        Api.allManga().get()
                .map(JSONAPIDocument<List<Manga>>::get)
                .flatMapIterable { l -> l }
                .test()
                .assertNoErrors()
                .assertComplete()
    }

    /**
     * Auth
     */
    @Test
    @Throws(Exception::class)
    fun auth1LoginTest() {
        if (TEST_USERNAME != null && TEST_PASSWORD != null) {
            Api.login(TEST_USERNAME, TEST_PASSWORD)
                    .test()
                    .assertNoErrors()
                    .assertComplete()
                    .assertValue { c -> c.accessToken != null }
                    .assertValue { c -> c.createdAt != null }
                    .assertValue { c -> c.expiresIn != null }
                    .assertValue { c -> c.refreshToken != null }
                    .assertValue { c -> c.scope.equals("public") }
                    .assertValue { c -> c.tokenType.equals("bearer") }
        }
    }

    @Test
    @Throws(Exception::class)
    fun auth2RefreshTest() {
        if (TEST_USERNAME != null && TEST_PASSWORD != null) {
            Api.login(TEST_USERNAME, TEST_PASSWORD)
                    .flatMap { c -> Api.refreshAuthToken(c.refreshToken!!) }
                    .test()
                    .assertNoErrors()
                    .assertComplete()
                    .assertValue { c -> c.accessToken != null }
                    .assertValue { c -> c.createdAt != null }
                    .assertValue { c -> c.expiresIn != null }
                    .assertValue { c -> c.refreshToken != null }
                    .assertValue { c -> c.scope.equals("public") }
                    .assertValue { c -> c.tokenType.equals("bearer") }
        }
    }

    /**
     * Library
     */
    @Test
    @Throws(Exception::class)
    fun auth3CreateLibraryEntryTest() {
        if (TEST_USERNAME != null && TEST_PASSWORD != null) {
            val anime = Anime()
            anime.id = "10909"
            val user = User()
            user.id = TEST_ACCOUNT_REMOTE_USER_ID
            val libraryEntry = LibraryEntry()
            libraryEntry.user = user
            libraryEntry.anime = anime
            libraryEntry.status = "completed"
            Api.login(TEST_USERNAME, TEST_PASSWORD)
                    .map { c -> c.accessToken!! }
                    .concatMap { c -> Api.createLibraryEntry(libraryEntry, c) }
                    .test()
                    .assertNoErrors()
                    .assertComplete()
        }
    }

    @Test
    @Throws(Exception::class)
    fun auth4UpdateLibraryEntryTest() {
        if (TEST_USERNAME != null && TEST_PASSWORD != null) {
            Api.login(TEST_USERNAME, TEST_PASSWORD)
                    .map { c -> c.accessToken!! }
                    .concatMap { c ->
                        Api.library(TEST_ACCOUNT_REMOTE_USER_ID)
                                .include(LibraryEntry.ANIME)
                                .get()
                                .map { m -> m.get() }
                                .flatMapIterable { library -> library }
                                .filter { entry -> entry.anime!!.id == "10909" }
                                .map { entry -> entry.id!!}
                                .concatMap { id ->
                                    val libraryEntry = LibraryEntry()
                                    libraryEntry.id = id
                                    libraryEntry.status = "dropped"
                                    Api.updateLibraryEntry(libraryEntry, c)
                                }
                    }
                    .test()
                    .assertNoErrors()
                    .assertComplete()
        }
    }

    /**
     * This deletes the library entry for anime with #id 10909 so that the createLibraryEntryTest doesn't fail (the next time tests are run).
     */
    @Test
    @Throws
    fun auth5DeleteLibraryEntryTest() {
        if (TEST_USERNAME != null && TEST_PASSWORD != null) {
            Api.login(TEST_USERNAME, TEST_PASSWORD)
                    .map { c -> c.accessToken!! }
                    .concatMap { c ->
                        Api.library(TEST_ACCOUNT_REMOTE_USER_ID)
                                .include(LibraryEntry.ANIME)
                                .get()
                                .map { m -> m.get() }
                                .flatMapIterable { library -> library }
                                .filter { entry -> entry.anime!!.id == "10909" }
                                .doOnNext { entry -> System.out.println(entry.id) }
                                .map { entry -> entry.id!!}
                                .concatMap { id ->
                                    Api.deleteLibraryEntry(id, c)
                                }
                    }
                    .test()
                    .assertNoErrors()
                    .assertComplete()
        }
    }

    @Test
    @Throws(Exception::class)
    fun getLibraryEntryTest() {
        Api.libraryEntry("17619547").get()
                .test()
                .assertNoErrors()
                .assertComplete()
    }

    @Test
    @Throws(Exception::class)
    fun getLibraryTest() {
        Api.library(TEST_ACCOUNT_REMOTE_USER_ID).get()
                .map(JSONAPIDocument<List<LibraryEntry>>::get)
                .flatMapIterable { l -> l }
                .test()
                .assertNoErrors()
                .assertComplete()
    }

    @Test
    @Throws(Exception::class)
    fun allLibraryEntriesTest() {
        Api.allLibraryEntries()
                .filter("userId", "59163")
                .filter("animeId", "3936")
                .page("limit", 1)
                .get()
                .map(JSONAPIDocument<List<LibraryEntry>>::get)
                .flatMapIterable { l -> l }
                .test()
                .assertNoErrors()
                .assertComplete()
    }

    @Test
    @Throws(Exception::class)
    fun libraryEntryForAnime() {
        // always use id=12 (one piece) as a library entry for one piece is kept in the test account
        Api.libraryEntry(TEST_ACCOUNT_REMOTE_USER_ID, JsonType("anime"), "12")
                .get()
                .map(JSONAPIDocument<List<LibraryEntry>>::get)
                .test()
                .assertValue { l -> l.size == 1 }
                .assertNoErrors()
                .assertComplete()
    }

}