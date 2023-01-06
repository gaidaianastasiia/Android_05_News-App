package com.example.android_05_news_app.presentation.home

import com.example.android_05_news_app.domain.model.NewsCategory
import com.example.android_05_news_app.domain.model.Post
import com.example.android_05_news_app.presentation.base.UiIntent

sealed class HomeIntent : UiIntent {
    data class OnPostsListScrolled(val index: Int) : HomeIntent()
    data class OnSelectedCategoryChanged(val category: NewsCategory) : HomeIntent()
    data class OnSearchInputChanged(val searchInput: String) : HomeIntent()
    object OnExecuteSearch : HomeIntent()
    data class OnNavigateToPostDetail(val post: Post) : HomeIntent()
}