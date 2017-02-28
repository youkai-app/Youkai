package app.youkai

import app.youkai.data.remote.Client
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test
import kotlin.test.assertEquals

class NetworkTests {

    /*
     * Checks that the interceptors in Client.kt are correctly adding the required headers to requests.
     */
    @Test
    @Throws(Exception::class)
    fun networkInterceptorsTest () {
        val server = MockWebServer()

        server.enqueue(MockResponse())
        server.enqueue(MockResponse())

        server.start()

        var response = Client.newCall(
                Request.Builder()
                .url(server.url("how/can/youkai/be/real"))
                .addHeader("not_a_header", "---").build()).execute()

        assertEquals("application/vnd.api+json", response.request().header("Accept"))
        assertEquals("application/vnd.api+json", response.request().header("Content-Type"))

        response = Client.newCall(
                Request.Builder()
                        .url(server.url("if/kitsu/isnt/real"))
                        .addHeader("not_a_header", "---")
                        .addHeader("Accept", "Don'TACCEPTS").build()).execute()

        /*
         * If the Accept header is already present, don't override it.
         * Always override response for requests to Kitsu.
         */
        assertEquals("Don'TACCEPTS", response.request().header("Accept"))
        assertEquals("application/vnd.api+json", response.request().header("Content-Type"))

        server.shutdown()
    }

}
