package com.example.android_05_news_app.presentation.post

import com.example.android_05_news_app.presentation.base.UiEvent

sealed class PostEvent : UiEvent {
    object NavigateToHome : PostEvent()
    data class NavigateToPostSource(val url : String) : PostEvent()
}
