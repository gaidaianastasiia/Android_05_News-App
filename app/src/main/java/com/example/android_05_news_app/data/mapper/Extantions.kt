package com.example.android_05_news_app.data.mapper

import com.example.android_05_news_app.data.network.model.ArticleAPIModel
import com.example.android_05_news_app.domain.model.Article

fun ArticleAPIModel.mapToDomainModel() =
    Article(author, title, description, url, image, category, published_at)

fun List<ArticleAPIModel>.mapToDomainList() = map { articleAPIModel ->
    articleAPIModel.mapToDomainModel()
}