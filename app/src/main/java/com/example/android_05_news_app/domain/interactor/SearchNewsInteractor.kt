package com.example.android_05_news_app.domain.interactor

import com.example.android_05_news_app.data.repository.ArticleRepository
import com.example.android_05_news_app.domain.model.Article
import javax.inject.Inject

class SearchNewsInteractor @Inject constructor(
    private val repository: ArticleRepository
) {
    suspend operator fun invoke(keywords: String, offset: Int) : List<Article> =
        repository.getNews(
            category = "",
            keywords = keywords,
            offset = offset,
        )
}