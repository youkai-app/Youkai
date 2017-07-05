package app.youkai.data.remote

import app.youkai.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

/*
 * Client to be used for Kitsu api calls only. Would need a separate client or else make
 * minor modifications to JsonHeadersInterceptor.kt to allow for non-json responses.
 */
val Client: OkHttpClient by lazy {
    OkHttpClient()
            .newBuilder()
            .addInterceptor(JsonHeadersInterceptor())
            .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE))
            .build()
}