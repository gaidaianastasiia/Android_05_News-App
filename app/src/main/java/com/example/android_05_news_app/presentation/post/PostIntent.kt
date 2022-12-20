package com.example.android_05_news_app.presentation.post

sealed class PostIntent {
    object OnBackButtonClick : PostIntent()
    data class OnGoToPostSourceClick(val url : String) : PostIntent()
}
