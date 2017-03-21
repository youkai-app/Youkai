package app.youkai

import app.youkai.data.service.Api
import junit.framework.Assert.assertNotNull
import org.junit.Test
import java.util.logging.Logger

class ApiTests {

    @Test
    @Throws(Exception::class)
    fun basicAnimeTest () {
        Api.anime("7442").get()
                .subscribe(
                        ::assertNotNull,
                        { l -> Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).info(l.message) }
        )
    }

    @Test
    @Throws(Exception::class)
    fun animeWithIncludesTest () {
        Api.anime("3919").include("castings", "episodes").get()
                .doOnComplete{ System.out.println("3919?includes=casting") }
                .subscribe(
                        { anime -> run {
                            assertNotNull(anime)
                            assertNotNull(anime?.castings)
                            assertNotNull(anime?.episodes)
                        }},
                        { t -> Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).info(t.message) }
                )
    }

}