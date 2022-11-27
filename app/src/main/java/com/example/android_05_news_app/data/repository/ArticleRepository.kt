package com.example.android_05_news_app.data.repository

import com.example.android_05_news_app.domain.model.Article

interface ArticleRepository {
    suspend fun getNews(category: String, keywords: String, offset: Int): List<Article>
}