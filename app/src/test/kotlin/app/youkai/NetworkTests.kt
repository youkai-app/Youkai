package app.youkai

import app.youkai.data.remote.Client
import okhttp3.Protocol
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
    fun addHeadersTest () {
        val server = MockWebServer()
        server.enqueue(MockResponse())
        server.start()

        val response = Client.newCall(
                Request.Builder()
                .url(server.url("how/can/youkai/be/real"))
                .addHeader("not_a_header", "---").build()).execute()

        assertEquals("application/vnd.api+json", response.request().header("Accept"))
        assertEquals("application/vnd.api+json", response.request().header("Content-Type"))

        server.shutdown()
    }


    @Test
    @Throws(Exception::class)
    fun changeHeadersTest () {
        val server = MockWebServer()
        server.enqueue(MockResponse())
        server.start()

        val response = Client.newCall(
                Request.Builder()
                        .url(server.url("if/kitsu/isnt/real"))
                        .addHeader("not_a_header", "---")
                        .addHeader("Accept", "Don'TACCEPT")
                        .addHeader("Content-Type", "acontenttypeyoudontwant").build()).execute()

        /*
         * If the Accept header is already present, don't override it.
         */
        assertEquals("Don'TACCEPT", response.request().header("Accept"))
        /*
         *
         */
        System.out.println("The content type is: " + response.request().header("Content-Type"))
        assertEquals("application/vnd.api+json", response.request().header("Content-Type"))

        server.shutdown()
    }

}
