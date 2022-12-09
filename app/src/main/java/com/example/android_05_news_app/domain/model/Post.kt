package com.example.android_05_news_app.domain.model

import androidx.compose.runtime.Stable

@Stable
data class Post(
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val published_at: String?,
    val content: String?,
)
