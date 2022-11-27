package com.example.android_05_news_app.domain.interactor

import com.example.android_05_news_app.data.repository.ArticleRepository
import com.example.android_05_news_app.domain.model.Article
import javax.inject.Inject

class GetNewsInteractor @Inject constructor(
    private val repository: ArticleRepository,
) {
    suspend operator fun invoke(selectedCategory: String, offset: Int): List<Article> =
        repository.getNews(
            category = selectedCategory,
            keywords = "",
            offset = offset,
        )
}