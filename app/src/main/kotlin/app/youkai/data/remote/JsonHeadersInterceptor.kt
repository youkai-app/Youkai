package app.youkai.data.remote

import okhttp3.Interceptor
import okhttp3.Response

/*
 * Edits all requests with the correct headers as according to the Kitsu API docs.
 * See http://docs.kitsu17.apiary.io/introduction/json-api/general-info
 */
class JsonHeadersInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
                .newBuilder()
                .addHeader("Accept", "application/vnd.api+json")
                .addHeader("Content-Type", "application/vnd.api+json")
                .build()

        return chain.proceed(request);

    }

}