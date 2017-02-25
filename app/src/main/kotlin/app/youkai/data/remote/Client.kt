package app.youkai.data.remote

import okhttp3.OkHttpClient

/*
 * Client to be used for Kitsu api calls only. Would need a separate client or else make
 * minor modifications to JsonHeadersInterceptor.kt to allow for non-json responses.
 */
val Client by lazy {
    OkHttpClient()
            .newBuilder()
            .addInterceptor(JsonHeadersInterceptor())
            .build()
}