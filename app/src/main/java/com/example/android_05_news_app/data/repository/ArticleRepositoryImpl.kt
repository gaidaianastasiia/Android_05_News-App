package com.example.android_05_news_app.data.repository

import com.example.android_05_news_app.data.mapper.mapToDomainList
import com.example.android_05_news_app.data.network.ArticleApiService
import com.example.android_05_news_app.domain.model.Article
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val articleApiService: ArticleApiService,
) : ArticleRepository {
    override suspend fun getNews(category: String, keywords: String, offset: Int): List<Article> {
        return articleApiService.getNews(category, keywords, offset).articles.mapToDomainList()
    }
}