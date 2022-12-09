package com.example.android_05_news_app.data.network

import com.example.android_05_news_app.data.network.response.ResponseAPIModel
import retrofit2.http.GET
import retrofit2.http.Query

interface PostsApiService {
    @GET("/v2/top-headlines")
    suspend fun getPosts(
        @Query("category") category: String,
        @Query("q") keywords: String,
        @Query("page") page: Int,
    ): ResponseAPIModel
}