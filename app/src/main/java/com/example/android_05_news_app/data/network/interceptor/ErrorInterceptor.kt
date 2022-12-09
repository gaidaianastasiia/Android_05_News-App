package com.example.android_05_news_app.data.network.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

object HttpStatusCodes {
    const val OK = 200
    const val BAD_REQUEST = 400
    const val UNAUTHORIZED = 401
    const val TOO_MANY_REQUESTS = 429
    const val SERVER_ERROR = 500
}

enum class ServerErrorsStatus(val value: Int) {
    OK(HttpStatusCodes.OK),
    BAD_REQUEST(HttpStatusCodes.BAD_REQUEST),
    UNAUTHORIZED(HttpStatusCodes.UNAUTHORIZED),
    TOO_MANY_REQUESTS(HttpStatusCodes.TOO_MANY_REQUESTS),
    SERVER_ERROR(HttpStatusCodes.SERVER_ERROR),
}

class ErrorInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        when(response.code) {
            ServerErrorsStatus.BAD_REQUEST.value -> { Log.d("###", "BAD_REQUEST") }
            ServerErrorsStatus.UNAUTHORIZED.value -> { Log.d("###", "UNAUTHORIZED") }
            ServerErrorsStatus.TOO_MANY_REQUESTS.value -> { Log.d("###", "TOO_MANY_REQUESTS") }
            ServerErrorsStatus.SERVER_ERROR.value -> { Log.d("###", "SERVER_ERROR") }
        }

        return response
    }
}