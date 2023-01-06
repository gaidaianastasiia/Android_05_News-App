package com.example.android_05_news_app.data.network.model

import com.google.gson.annotations.SerializedName

data class PostAPIModel(
    @SerializedName("author")
    val author: String?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("url")
    val url: String?,

    @SerializedName("urlToImage")
    val urlToImage: String?,

    @SerializedName("published_at")
    val published_at: String?,

    @SerializedName("content")
    val content: String?,
)
