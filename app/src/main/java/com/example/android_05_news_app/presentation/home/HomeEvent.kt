package com.example.android_05_news_app.presentation.home

sealed class HomeEvent {
    data class ShowError(val error: String?) : HomeEvent()
}