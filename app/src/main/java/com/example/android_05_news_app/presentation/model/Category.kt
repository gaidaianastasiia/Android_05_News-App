package com.example.android_05_news_app.presentation.model

import com.example.android_05_news_app.domain.model.NewsCategory

data class Category(
    val name: NewsCategory,
    val isSelected: Boolean,
)
