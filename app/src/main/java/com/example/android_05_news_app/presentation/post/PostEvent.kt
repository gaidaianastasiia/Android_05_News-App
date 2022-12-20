package com.example.android_05_news_app.presentation.post

sealed class PostEvent {
    object NavigateToHome : PostEvent()
    data class NavigateToPostSource(val url : String) : PostEvent()
}
