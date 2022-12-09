package com.example.android_05_news_app.data.network.response

import com.example.android_05_news_app.data.network.model.PostAPIModel
import com.google.gson.annotations.SerializedName

data class ResponseAPIModel(
    @SerializedName("status")
    val status: String,

    @SerializedName("totalResults")
    val totalResults: Int,

    @SerializedName("articles")
    val postsList: List<PostAPIModel>,
)
