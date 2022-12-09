package com.example.android_05_news_app.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_05_news_app.domain.interactor.GetPostsFeedInteractor
import com.example.android_05_news_app.domain.interactor.SearchPostsInteractor
import com.example.android_05_news_app.domain.model.NewsCategories
import com.example.android_05_news_app.domain.model.Post
import com.example.android_05_news_app.presentation.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPostsFeed: GetPostsFeedInteractor,
    private val searchPosts: SearchPostsInteractor,
) : ViewModel() {

    private val _state = MutableStateFlow(
        HomeState(
            isLoading = true,
            postsList = null,
            categoriesList = getInitialCategoriesList(),
        ))
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<HomeEvent>()
    val event = _event.asSharedFlow()

    init {
        fetchPosts(NewsCategories.GENERAL.value)
    }

    fun handleIntent(homeIntent: HomeIntent) {
        when (homeIntent) {
            is HomeIntent.OnSelectedCategoryChanged -> {
                onCategoryChanged(homeIntent.category)
            }
        }
    }

    private fun fetchPosts(category: String) {
        viewModelScope.launch {
            try {
                val postsList = getPostsFeed(category, 1)
                _state.value = _state.value.copy(
                    isLoading = false,
                    postsList = postsList
                )
            } catch (e: Exception) {
                _event.emit(HomeEvent.ShowError(e.message))
            }
        }
    }

    private fun getInitialCategoriesList(): List<Category> {
        return NewsCategories.values().map { category ->
            Category(
                name = category,
                isSelected = category == NewsCategories.GENERAL
            )
        }
    }

    private fun onCategoryChanged(category: NewsCategories) {
        updateCategoryList(category)
        fetchPosts(category.value)
    }

    private fun updateCategoryList(updatedCategory: NewsCategories) {
        val updatedList = _state.value.categoriesList.map { category ->
            category.copy(isSelected = category.name == updatedCategory)
        }

        _state.value = _state.value.copy(categoriesList = updatedList)
    }
}
