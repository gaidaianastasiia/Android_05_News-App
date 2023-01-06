package com.example.android_05_news_app.presentation.post

import androidx.compose.runtime.Stable
import com.example.android_05_news_app.domain.model.Post
import com.example.android_05_news_app.presentation.base.UiState

@Stable
data class PostState(val post: Post) : UiState
