package com.example.android_05_news_app.presentation.home

import com.example.android_05_news_app.domain.model.Post
import com.example.android_05_news_app.presentation.model.Category

data class HomeState(
    val isLoading: Boolean,
    val emptyState: Boolean,
    val postsList: List<Post>,
    val categoriesList: List<Category>,
    val searchInput: String,
)