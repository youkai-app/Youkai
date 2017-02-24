package app.youkai.data.remote

import okhttp3.OkHttpClient


val Client by lazy {
    OkHttpClient()
            .newBuilder()
            .addInterceptor(JsonHeadersInterceptor())
            .build()
}