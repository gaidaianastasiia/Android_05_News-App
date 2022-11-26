package com.example.android_05_news_app.data.network.model

import com.google.gson.annotations.SerializedName

data class ArticleAPIModel(
    @SerializedName("author")
    val author: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("image")
    val image: String,

    @SerializedName("category")
    val category: String,

    @SerializedName("published_at")
    val published_at: String,
)
