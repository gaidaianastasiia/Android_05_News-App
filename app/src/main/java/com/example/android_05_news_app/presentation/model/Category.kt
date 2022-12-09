package com.example.android_05_news_app.presentation.model

import com.example.android_05_news_app.domain.model.NewsCategories

data class Category(
    val name: NewsCategories,
    val isSelected: Boolean,
)
