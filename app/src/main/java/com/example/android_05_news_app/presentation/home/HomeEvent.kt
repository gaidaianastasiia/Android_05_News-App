package com.example.android_05_news_app.presentation.home

import com.example.android_05_news_app.domain.model.Post
import com.example.android_05_news_app.presentation.base.UiEvent

sealed class HomeEvent : UiEvent {
    data class ShowError(val errorMessage: Int) : HomeEvent()
    data class NavigateToPostDetail(val post: Post) : HomeEvent()
}