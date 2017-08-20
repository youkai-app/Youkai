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
    fun basicAnimeTest() {
        Api.anime("7442").get()
                .map(JSONAPIDocument<Anime>::get)
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertValue { a -> a.id.equals("7442") }
    }

    @Test
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
    fun searchAnimeTest() {
        Api.searchAnime("goddamnit").get()
                .map(JSONAPIDocument<List<Anime>>::get)
                .flatMapIterable { l -> l }
                .test()
                .assertNoErrors()
                .assertComplete()
    }

    @Test
    fun allAnimeTest() {
        Api.allAnime().get()
                .map(JSONAPIDocument<List<Anime>>::get)
                .flatMapIterable { l -> l }
                .test()
                .assertNoErrors()
                .assertComplete()
    }

    @Test
    fun languagesForAnimeTest() {
        Api.languagesForAnime("1").get()
                .flatMapIterable { l -> l }
                .doOnNext { s -> System.out.println(s) }
                .test()
                .assertNoErrors()
                .assertComplete()
    }

    /**
     * Manga
     */
    @Test
    fun searchMangaTest() {
        Api.searchManga("goddamnit").get()
                .map(JSONAPIDocument<List<Manga>>::get)
                .flatMapIterable { l -> l }
                .test()
                .assertNoErrors()
                .assertComplete()
    }

    @Test
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
    fun getLibraryEntryTest() {
        Api.libraryEntry("17619547").get()
                .test()
                .assertNoErrors()
                .assertComplete()
    }

    @Test
    fun getLibraryTest() {
        Api.library(TEST_ACCOUNT_REMOTE_USER_ID).get()
                .map(JSONAPIDocument<List<LibraryEntry>>::get)
                .flatMapIterable { l -> l }
                .test()
                .assertNoErrors()
                .assertComplete()
    }

    @Test
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
    fun libraryEntryForAnime() {
        // always use id=12 (one piece) as a library entry for one piece is kept in the test account
        Api.libraryEntryForAnime(TEST_ACCOUNT_REMOTE_USER_ID, "12")
                .get()
                .map(JSONAPIDocument<List<LibraryEntry>>::get)
                .test()
                .assertValue { l -> l.size == 1 }
                .assertNoErrors()
                .assertComplete()
    }

    @Test
    fun libraryEntryForManga() {
        Api.libraryEntryForManga(TEST_ACCOUNT_REMOTE_USER_ID, "12")
                .get()
                .map(JSONAPIDocument<List<LibraryEntry>>::get)
                .test()
                // no entry for this manga
                .assertValue { l -> l.isEmpty() }
                .assertNoErrors()
                .assertComplete()
    }

    /**
     * Favorites
     */
    @Test
    fun allFavoritesTest() {
        Api.allFavorites()
                .filter("userId", TEST_ACCOUNT_REMOTE_USER_ID)
                .filter("itemId", "12")
                .filter("itemType", "Anime")
                .page("limit", 1)
                .get()
                .map(JSONAPIDocument<List<Favorite>>::get)
                .flatMapIterable { l -> l }
                .test()
                .assertNoErrors()
                .assertComplete()
    }

    @Test
    fun getFavoriteTest() {
        Api.favorite("697162").get()
                .test()
                .assertNoErrors()
                .assertComplete()
    }

    @Test
    fun favoriteForAnimeTest() {
        // always use id=12 (one piece) as a library entry for one piece is a favorite in the test account
        Api.favoriteForAnime(TEST_ACCOUNT_REMOTE_USER_ID, "12")
                .get()
                .map(JSONAPIDocument<List<Favorite>>::get)
                .test()
                .assertValue { l -> l.size == 1 }
                .assertNoErrors()
                .assertComplete()
    }

    @Test
    fun favoriteForMangaTest() {
        Api.favoriteForManga(TEST_ACCOUNT_REMOTE_USER_ID, "12")
                .get()
                .map(JSONAPIDocument<List<Favorite>>::get)
                .test()
                // no favorites for this manga
                .assertValue { l -> l.isEmpty() }
                .assertNoErrors()
                .assertComplete()
    }

    @Test
    fun createFavorite() {
        val favorite = Favorite()
        favorite.user = User()
        favorite.user!!.id = TEST_ACCOUNT_REMOTE_USER_ID
        // TODO: finish test (needs polymorph fix)
    }

    /**
     * Characters
     */
    @Test
    fun allCharactersTest() {
        Api.allCharacters().get()
                .map(JSONAPIDocument<List<Character>>::get)
                .flatMapIterable { l -> l }
                .test()
                .assertNoErrors()
                .assertComplete()
    }

    @Test
    fun getCharacterTest() {
        Api.character("2").get()
                .test()
                .assertValue { it -> it.get() is Character }
                .assertValue { it -> it.get() != null }
                .assertNoErrors()
                .assertComplete()
    }

    @Test
    fun charactersForAnimeTest() {
        Api.charactersForAnime("1").get()
                .map(JSONAPIDocument<List<AnimeCharacter>>::get)
                .flatMapIterable { l -> l }
                .test()
                .assertNoErrors()
                .assertComplete()
    }

    /**
     * Castings
     */
    @Test
    fun allCastingsTest() {
        Api.allCastings().get()
                .map(JSONAPIDocument<List<Casting>>::get)
                .flatMapIterable { l -> l }
                .test()
                .assertNoErrors()
                .assertComplete()
    }

    @Test
    fun getCastingTest() {
        Api.casting("4").get()
                .test()
                .assertValue { it -> it.get() is Casting }
                .assertValue { it -> it.get() != null }
                .assertNoErrors()
                .assertComplete()
    }

    @Test
    fun castingsForAnimeTest() {
        Api.castingsForAnime("1").get()
                .map(JSONAPIDocument<List<Casting>>::get)
                .flatMapIterable { l -> l }
                .test()
                .assertNoErrors()
                .assertComplete()
    }

    @Test
    fun castingsForMangaTest() {
        Api.castingsForManga("38")
                .filter("isCharacter", "true")
                .include("character", "person")
                .get()
                .map(JSONAPIDocument<List<Casting>>::get)
                .flatMapIterable { l -> l }
                .test()
                .assertNoErrors()
                .assertComplete()
    }


    /**
     * Reactions
     */
    @Test
    fun allReactionsTest() {
        Api.allReactions().get()
                .map(JSONAPIDocument<List<Reaction>>::get)
                .flatMapIterable { l -> l }
                .test()
                .assertNoErrors()
                .assertComplete()
    }

    @Test
    fun getReactionTest() {
        Api.reaction("4").get()
                .test()
                .assertValue { it -> it.get() is Reaction }
                .assertValue { it -> it.get() != null }
                .assertNoErrors()
                .assertComplete()
    }

    @Test
    fun reactionsForAnimeTest() {
        Api.reactionsForAnime("1").get()
                .map(JSONAPIDocument<List<Reaction>>::get)
                .flatMapIterable { l -> l }
                .test()
                .assertNoErrors()
                .assertComplete()
    }

    @Test
    fun reactionsForMangaTest() {
        Api.reactionsForManga("38")
                .get()
                .map(JSONAPIDocument<List<Reaction>>::get)
                .flatMapIterable { l -> l }
                .test()
                .assertNoErrors()
                .assertComplete()
    }

}