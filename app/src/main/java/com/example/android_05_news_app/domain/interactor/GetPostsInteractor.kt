package com.example.android_05_news_app.domain.interactor

import com.example.android_05_news_app.data.repository.PostsRepository
import com.example.android_05_news_app.domain.model.Post
import javax.inject.Inject

class GetPostsInteractor @Inject constructor(
    private val repository: PostsRepository,
) {
    suspend operator fun invoke(selectedCategory: String, page: Int): List<Post> =
        repository.getPosts(
            category = selectedCategory,
            keywords = "",
            page = page,
        )
}