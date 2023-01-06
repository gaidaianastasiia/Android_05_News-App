package com.example.android_05_news_app.data.repository

import com.example.android_05_news_app.data.mapper.mapToDomainList
import com.example.android_05_news_app.data.network.PostsApiService
import com.example.android_05_news_app.domain.model.Post
import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(
    private val postApiService: PostsApiService,
) : PostsRepository {
    override suspend fun getPosts(category: String, keywords: String, page: Int): List<Post> {
        return postApiService.getPosts(category, keywords, page).postsList.mapToDomainList()
    }
}