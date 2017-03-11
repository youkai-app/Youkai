package app.youkai

import app.youkai.data.service.Api
import org.junit.Test
import java.util.logging.Logger

class ApiTests {

    @Test
    @Throws(Exception::class)
    fun animeTest () {
        Api.anime("7442").get()
                .doOnSubscribe { System.out.println("subscribing") }
                .subscribe(
                { m -> System.out.println(m.canonicalTitle) },
                { l -> Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).info(l.message)}
        );
    }

}