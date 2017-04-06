package app.youkai

import app.youkai.data.models.Anime
import app.youkai.data.models.BaseMedia
import app.youkai.data.service.Api
import org.junit.Test

class ApiTests {

    /**
     * Set your username and password for api tests. Remember to remove before committing anything.
     * Tests will pass if null because they aren't run.
     */
    val testUsername = null
    val testPassword = null

    @Test
    @Throws(Exception::class)
    fun basicAnimeTest() {
        Api.anime("7442").get()
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertValue { a -> a != null }
    }

    @Test
    @Throws(Exception::class)
    fun animeWithIncludesTest() {
        Api.anime("3919").include(BaseMedia.CASTINGS, Anime.EPISODES).get()
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertValue { a -> a != null }
                .assertValue { a -> a.castings != null }
                .assertValue { a -> a.episodes != null }
    }

    @Test
    @Throws(Exception::class)
    fun fullAnimeTest() {
        Api.anime("1")
                .include(BaseMedia.GENRES, BaseMedia.GENRES, BaseMedia.INSTALLMENTS, BaseMedia.MAPPINGS, BaseMedia.MEDIA_RELATIONSHIPS,
                        BaseMedia.REVIEWS, Anime.EPISODES, Anime.PRODUCTIONS, Anime.CHARACTERS, Anime.STAFF)
                .get()
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertValue { a -> a != null }
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
    fun loginTest() {
        if (testUsername != null && testPassword != null) {
            Api.login(testUsername!!, testPassword!!)
                    .test()
                    .assertNoErrors()
                    .assertComplete()
                    .assertValue { c -> c != null }
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
    fun authRefreshTest() {
        if (testUsername != null && testPassword != null) {
            Api.login(testUsername!!, testPassword!!)
                    .flatMap { c -> Api.refreshAuthToken(c.refreshToken!!) }
                    .test()
                    .assertNoErrors()
                    .assertComplete()
                    .assertValue { c -> c != null }
                    .assertValue { c -> c.accessToken != null }
                    .assertValue { c -> c.createdAt != null }
                    .assertValue { c -> c.expiresIn != null }
                    .assertValue { c -> c.refreshToken != null }
                    .assertValue { c -> c.scope.equals("public") }
                    .assertValue { c -> c.tokenType.equals("bearer") }
        }
    }

}