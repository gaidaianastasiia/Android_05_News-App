package com.example.android_05_news_app.presentation.post

import androidx.compose.runtime.Stable
import com.example.android_05_news_app.domain.model.Post

@Stable
data class PostState(val post: Post)
