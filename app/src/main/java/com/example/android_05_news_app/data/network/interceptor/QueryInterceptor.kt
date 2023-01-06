package com.example.android_05_news_app.data.network.interceptor

import com.example.android_05_news_app.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

object QueryParameterName {
    const val API_KEY = "apiKey"
    const val COUNTRY = "country"
}
private const val DEFAULT_COUNTRY = "us"

class QueryInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val originalHttpUrl: HttpUrl = original.url

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter(QueryParameterName.API_KEY, BuildConfig.NEWS_URL_TOKEN)
            .addQueryParameter(QueryParameterName.COUNTRY, DEFAULT_COUNTRY)
            .build()

        val requestBuilder: Request.Builder = original.newBuilder()
            .url(url)

        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }
}