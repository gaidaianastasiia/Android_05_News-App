package com.example.android_05_news_app.presentation.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.android_05_news_app.domain.model.Post
import com.example.android_05_news_app.presentation.home.HomeState

@Composable
fun PostCards(
    postsList: List<Post>,
) {
    LazyColumn {
        items(postsList) { post ->
            PostCard(post = post)
        }
    }
}