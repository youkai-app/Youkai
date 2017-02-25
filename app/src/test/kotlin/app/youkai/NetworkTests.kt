package app.youkai

import app.youkai.data.remote.Client
import okhttp3.Request
import org.junit.Test
import java.util.logging.Logger
import kotlin.test.assertEquals

class NetworkTests {

    val logger = Logger.getAnonymousLogger()

    /*
     * Checks that the interceptors in Client.kt are correctly adding the required headers to requests.
     */
    @Test
    @Throws(Exception::class)
    fun networkInterceptorsTest () {
        var response = Client.newCall(
                Request.Builder()
                .url("http://xiprox.me")
                .addHeader("not_a_header", "---").build()).execute()

        assertEquals("application/vnd.api+json", response.request().header("Accept"))
        assertEquals("application/vnd.api+json", response.request().header("Content-Type"))

        response = Client.newCall(
                Request.Builder()
                        .url("http://xiprox.me")
                        .addHeader("not_a_header", "---")
                        .addHeader("Accept", "Don'TACCEPTS").build()).execute()

        /*
         * If the Accept header is already present, don't override it.
         * Always override response for requests to Kitsu.
         */
        assertEquals("Don'TACCEPTS", response.request().header("Accept"))
        assertEquals("application/vnd.api+json", response.request().header("Content-Type"))
    }

}
