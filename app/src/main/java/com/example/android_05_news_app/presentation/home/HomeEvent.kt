package com.example.android_05_news_app.presentation.home

import com.example.android_05_news_app.domain.model.Post

sealed class HomeEvent {
    data class ShowError(val errorMessage: Int) : HomeEvent()
    data class NavigateToPostDetail(val post: Post) : HomeEvent()
}