package app.youkai

import app.youkai.data.models.Anime
import app.youkai.data.service.Api
import io.reactivex.observers.TestObserver
import junit.framework.Assert.assertNotNull
import org.junit.Test

class ApiTests {

    @Test
    @Throws(Exception::class)
    fun basicAnimeTest () {
        val testSubscriber: TestObserver<Anime> = TestObserver()

        Api.anime("7442").get().subscribe(::assertNotNull)

        testSubscriber.assertNoErrors()
    }

    @Test
    @Throws(Exception::class)
    fun animeWithIncludesTest () {
        val testSubscriber: TestObserver<Anime> = TestObserver()

        Api.anime("3919").include("castings", "episodes").get()
                .subscribe(
                        { anime -> run {
                            assertNotNull(anime)
                            assertNotNull(anime?.castings)
                            assertNotNull(anime?.episodes)
                        }}
                )

        testSubscriber.assertNoErrors()
    }

    @Test
    @Throws(Exception::class)
    fun fullAnimeTest () {
        val testSubscriber: TestObserver<Anime> = TestObserver()

        Api.anime("1").include("genres", "castings", "installments", "mappings", "mediaRelationships",
                "reviews", "episodes", "animeProductions", "animeCharacters", "animeStaff").get()
                .doOnNext( { anime -> run {
                    assertNotNull(anime)
                    assertNotNull(anime!!.genres)
                    assertNotNull(anime.genreLinks)
                    assertNotNull(anime.castings)
                    assertNotNull(anime.castingLinks)
                    assertNotNull(anime.installments)
                    assertNotNull(anime.installmentLinks)
                    assertNotNull(anime.mappings)
                    assertNotNull(anime.mappingLinks)
                    assertNotNull(anime.medias)
                    assertNotNull(anime.mediaLinks)
                    assertNotNull(anime.reviews)
                    assertNotNull(anime.reviewLinks)
                    assertNotNull(anime.episodes)
                    assertNotNull(anime.episodeLinks)
                    assertNotNull(anime.productions)
                    assertNotNull(anime.productionLinks)
                    assertNotNull(anime.animeCharacters)
                    assertNotNull(anime.animeCharacterLinks)
                    assertNotNull(anime.staff)
                    assertNotNull(anime.staffLinks) }} )
                .subscribe(testSubscriber)

        testSubscriber.assertNoErrors()
    }

}