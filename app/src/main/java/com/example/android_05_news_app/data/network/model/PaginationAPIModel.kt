package com.example.android_05_news_app.data.network.model

import com.google.gson.annotations.SerializedName

data class PaginationAPIModel(
    @SerializedName("limit")
    val limit: String,

    @SerializedName("offset")
    val offset: String,

    @SerializedName("count")
    val count: String,

    @SerializedName("total")
    val total: String,
)
