package com.example.android_05_news_app.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_05_news_app.data.repository.PostsRepository
import com.example.android_05_news_app.domain.interactor.GetPostsFeedInteractor
import com.example.android_05_news_app.domain.interactor.SearchPostsInteractor
import com.example.android_05_news_app.domain.model.NewsCategories
import com.example.android_05_news_app.presentation.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val MINIMUM_SEARCH_REQUEST_CHARACTERS_NUMBER = 3

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPostsFeed: GetPostsFeedInteractor,
    private val searchPosts: SearchPostsInteractor,
) : ViewModel() {

    private val _event = MutableSharedFlow<HomeEvent>()
    private var searchRequestJob: Job? = null
    private val _state = MutableStateFlow(
        HomeState(
            isLoading = true,
            emptyState = false,
            postsList = null,
            categoriesList = getInitialCategoriesList(),
            searchInput = "",
        )
    )
    val state = _state.asStateFlow()
    val event = _event.asSharedFlow()

    init {
        getPosts(NewsCategories.GENERAL.value)
    }

    fun handleIntent(homeIntent: HomeIntent) {
        when (homeIntent) {
            is HomeIntent.OnSelectedCategoryChanged -> {
                onCategoryChanged(homeIntent.category)
            }
            is HomeIntent.OnSearchInputChanged -> {
                onSearchInputChanged(homeIntent.searchInput)
            }
            is HomeIntent.OnExecuteSearch -> {
                onExecuteSearch()
            }
        }
    }

    private fun getPosts(category: String) {
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

    private fun searchPosts(keyWords: String) {
        searchRequestJob?.cancel()
        searchRequestJob = viewModelScope.launch {
            try {
                val postsList = searchPosts(keyWords, 1)

                if (postsList.isEmpty()) {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        emptyState = true,
                        postsList = emptyList()
                    )

                } else {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        emptyState = false,
                        postsList = postsList,
                    )
                }
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
        getPosts(category.value)
    }

    private fun onSearchInputChanged(searchInput: String) {
        updateSearchInput(searchInput)

    }

    private fun onExecuteSearch() {
        val searchInput = _state.value.searchInput

        if (searchInput.length >= MINIMUM_SEARCH_REQUEST_CHARACTERS_NUMBER) {
            searchPosts(searchInput)
        }

        updateSearchInput("")
        updateCategoryList(null)
    }

    private fun updateCategoryList(updatedCategory: NewsCategories?) {
        val updatedList = _state.value.categoriesList.map { category ->
            category.copy(isSelected = category.name == updatedCategory)
        }

        _state.value = _state.value.copy(categoriesList = updatedList)
    }

    private fun updateSearchInput(updatedSearchInput: String) {
        _state.value = _state.value.copy(searchInput = updatedSearchInput)
    }
}
