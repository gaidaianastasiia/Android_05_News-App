package com.example.android_05_news_app.domain.model

data class Article(
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val image: String,
    val category: String,
    val published_at: String,
)
