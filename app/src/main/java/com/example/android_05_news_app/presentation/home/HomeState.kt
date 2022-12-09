package com.example.android_05_news_app.presentation.home

import com.example.android_05_news_app.domain.model.Post
import com.example.android_05_news_app.presentation.model.Category

//
//sealed class HomeState2 {
//    object Loading : HomeState()
//    data class PostsFeed(val postsList: List<Post>, val category: String) : HomeState()
//    data class NewsCategories(val newsCategoriesList: List<CategoryItem>)
//}

data class HomeState(
    val isLoading: Boolean,
    val postsList: List<Post>?,
    val categoriesList: List<Category>,
)