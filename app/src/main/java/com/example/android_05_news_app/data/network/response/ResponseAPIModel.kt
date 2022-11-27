package com.example.android_05_news_app.data.network.response

import com.example.android_05_news_app.data.network.model.ArticleAPIModel
import com.example.android_05_news_app.data.network.model.PaginationAPIModel
import com.google.gson.annotations.SerializedName

data class ResponseAPIModel(
    @SerializedName("pagination")
    val pagination: PaginationAPIModel,

    @SerializedName("data")
    val articles: List<ArticleAPIModel>,
)
