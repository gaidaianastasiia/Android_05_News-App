package com.example.android_05_news_app.presentation.home

import com.example.android_05_news_app.domain.model.NewsCategories
import com.example.android_05_news_app.domain.model.Post

sealed class HomeIntent {
    data class OnScrollPostsListListener(val index: Int) : HomeIntent()
    data class OnSelectedCategoryChanged(val category: NewsCategories) : HomeIntent()
    data class OnSearchInputChanged(val searchInput: String) : HomeIntent()
    object OnExecuteSearch : HomeIntent()
    data class OnNavigateToPostDetail(val post: Post) : HomeIntent()
}