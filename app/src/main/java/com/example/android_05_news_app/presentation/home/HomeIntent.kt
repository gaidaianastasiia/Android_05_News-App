package com.example.android_05_news_app.presentation.home

import com.example.android_05_news_app.domain.model.NewsCategories

sealed class HomeIntent {
    data class OnSelectedCategoryChanged(val category: NewsCategories) : HomeIntent()
}