package com.example.android_05_news_app.presentation.home

import androidx.compose.runtime.Stable
import com.example.android_05_news_app.domain.model.Post
import com.example.android_05_news_app.presentation.model.Category

@Stable
data class HomeState(
    val isLoading: Boolean,
    val isEmptyState: Boolean,
    val postsList: List<Post>,
    val categoriesList: List<Category>,
    val searchInput: String,
)