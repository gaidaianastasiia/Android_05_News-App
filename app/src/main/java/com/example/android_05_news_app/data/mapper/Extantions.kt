package com.example.android_05_news_app.data.mapper

import com.example.android_05_news_app.data.network.model.PostAPIModel
import com.example.android_05_news_app.domain.model.Post

fun PostAPIModel.mapToDomainModel() =
    Post(
        author = author,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        published_at = published_at,
        content = content,
    )

fun List<PostAPIModel>.mapToDomainList() = map { it.mapToDomainModel() }