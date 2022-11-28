package com.example.android_05_news_app.data.network

import com.example.android_05_news_app.data.network.response.ResponseAPIModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleApiService {
    @GET("news")
    fun getNews(
        @Query("categories") category: String,
        @Query("keywords") keywords: String,
        @Query("offset") offset: Int,
    ): ResponseAPIModel
}