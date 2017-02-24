package app.youkai

import app.youkai.data.remote.Client
import okhttp3.Request
import org.junit.Test
import kotlin.test.assertEquals

class NetworkTests {

    /*
     * Checks that the interceptors in Client.kt are correctly adding the required headers to requests.
     */
    @Test
    @Throws(Exception::class)
    fun networkInterceptorsTest () {
        val response = Client.newCall(
                Request.Builder()
                .url("http://xiprox.me")
                .addHeader("not_a_header", "---").build()).execute()

        assertEquals("application/vnd.api+json", response.request().header("Accept"))
        assertEquals("application/vnd.api+json", response.request().header("Content-Type"))
    }

}
