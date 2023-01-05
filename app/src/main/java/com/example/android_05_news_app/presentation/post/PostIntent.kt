package com.example.android_05_news_app.presentation.post

import com.example.android_05_news_app.presentation.base.UiIntent

sealed class PostIntent : UiIntent {
    object OnBackButtonClick : PostIntent()
    data class OnGoToPostSourceClick(val url : String) : PostIntent()
}
