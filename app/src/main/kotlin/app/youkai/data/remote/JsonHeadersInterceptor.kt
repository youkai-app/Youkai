package app.youkai.data.remote

import okhttp3.Interceptor
import okhttp3.Response

/*
 * Edits all requests to have the correct headers as according to the Kitsu API docs.
 * See http://docs.kitsu17.apiary.io/introduction/json-api/general-info
 */
class JsonHeadersInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
                .addHeader("Content-Type", "application/vnd.api+json")

        // some requests require different Accept headers (E.G. login)
        if (chain.request().header("Accept").isNullOrEmpty()) {
            requestBuilder.addHeader("Accept", "application/vnd.api+json")
        }

        return chain.proceed(requestBuilder.build())
    }

}