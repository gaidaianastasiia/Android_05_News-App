package com.example.android_05_news_app.data.network.interceptor

import com.example.android_05_news_app.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

private const val DEFAULT_LANGUAGE = "en"

class QueryInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val originalHttpUrl: HttpUrl = original.url

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("access_key", BuildConfig.NEWS_URL_TOKEN)
            .addQueryParameter("languages", DEFAULT_LANGUAGE)
            .build()


        val requestBuilder: Request.Builder = original.newBuilder()
            .url(url)

        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }
}