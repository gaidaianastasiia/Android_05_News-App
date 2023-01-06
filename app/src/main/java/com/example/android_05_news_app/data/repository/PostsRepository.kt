package com.example.android_05_news_app.data.repository

import com.example.android_05_news_app.domain.model.Post

interface PostsRepository {
    suspend fun getPosts(category: String, keywords: String, page: Int): List<Post>
}