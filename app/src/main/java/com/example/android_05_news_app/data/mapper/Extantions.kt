package com.example.android_05_news_app.data.mapper

import com.example.android_05_news_app.data.network.model.PostAPIModel
import com.example.android_05_news_app.domain.model.Post


fun PostAPIModel.mapToDomainModel() =
    Post(author, title, description, url, urlToImage, published_at, content)

fun List<PostAPIModel>.mapToDomainList() = map { it.mapToDomainModel() }