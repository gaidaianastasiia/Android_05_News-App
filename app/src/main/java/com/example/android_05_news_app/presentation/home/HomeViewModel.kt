package com.example.android_05_news_app.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_05_news_app.domain.interactor.GetPostsFeedInteractor
import com.example.android_05_news_app.domain.interactor.SearchPostsInteractor
import com.example.android_05_news_app.domain.model.NewsCategories
import com.example.android_05_news_app.domain.model.Post
import com.example.android_05_news_app.presentation.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val DEFAULT_PAGE_NUMBER = 1
private const val PAGE_SIZE = 20
private const val PAGINATION_OFFSET = 5
private const val MINIMUM_SEARCH_REQUEST_CHARACTERS_NUMBER = 1

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPostsFeedInteractor: GetPostsFeedInteractor,
    private val searchPostsInteractor: SearchPostsInteractor,
) : ViewModel() {

    private val _event = MutableSharedFlow<HomeEvent>()
    val event = _event.asSharedFlow()

    private val _state = MutableStateFlow(
        HomeState(
            isLoading = true,
            emptyState = false,
            postsList = emptyList(),
            categoriesList = getInitialCategoriesList(),
            searchInput = "",
        )
    )
    val state = _state.asStateFlow()

    private var searchRequestJob: Job? = null
    private var page = DEFAULT_PAGE_NUMBER
    private var currentCategory: NewsCategories? = NewsCategories.GENERAL

    init {
        currentCategory?.let {
            getPosts(it.value, page)
        }
    }

    fun handleIntent(homeIntent: HomeIntent) {
        when (homeIntent) {
            is HomeIntent.OnScrollPostsListListener -> {
                onScrollPostsList(homeIntent.index)
            }
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

    private fun onScrollPostsList(scrollPosition: Int) {
        if (scrollPosition >= ((PAGE_SIZE * page) - PAGINATION_OFFSET)) {
            incrementPage()
            val category = currentCategory

            if (category == null) {
                searchPosts(_state.value.searchInput, page)
            } else {
                getPosts(category.value, page)
            }
        }
    }

    private fun onCategoryChanged(category: NewsCategories) {
        resetPage()
        resetPostsList()
        updateCategoryList(category)
        getPosts(category.value, page)
    }

    private fun onSearchInputChanged(searchInput: String) {
        updateSearchInput(searchInput)
    }

    private fun onExecuteSearch() {
        val searchInput = _state.value.searchInput

        if (searchInput.length >= MINIMUM_SEARCH_REQUEST_CHARACTERS_NUMBER) {
            resetPage()
            resetPostsList()
            searchPosts(searchInput, page)
        } else {
            _state.value = _state.value.copy(isLoading = false)
        }

        updateSearchInput("")
        updateCategoryList(null)
    }

    private fun getPosts(category: String, page: Int) {
        viewModelScope.launch {
            try {
                val postsList = getPostsFeedInteractor(category, page)

                if (page != DEFAULT_PAGE_NUMBER) {
                    appendPosts(postsList)
                } else {
                    setPostsList(postsList)
                }

            } catch (e: Exception) {
                _event.emit(HomeEvent.ShowError(e.message))
            }
        }
    }

    private fun searchPosts(keyWords: String, page: Int) {
        searchRequestJob?.cancel()
        searchRequestJob = viewModelScope.launch {
            try {
                val postsList = searchPostsInteractor(keyWords, page)

                if (postsList.isEmpty()) {
                    setEmptyState()
                } else {
                    setPostsList(postsList)
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
                isSelected = category == currentCategory
            )
        }
    }

    private fun updateCategoryList(updatedCategory: NewsCategories?) {
        currentCategory = updatedCategory
        val updatedList = _state.value.categoriesList.map { category ->
            category.copy(isSelected = category.name == updatedCategory)
        }

        _state.value = _state.value.copy(categoriesList = updatedList)
    }

    private fun updateSearchInput(updatedSearchInput: String) {
        _state.value = _state.value.copy(searchInput = updatedSearchInput)
    }

    private fun setPostsList(postsList: List<Post>) {
        _state.value = _state.value.copy(
            isLoading = false,
            postsList = postsList
        )
    }

    private fun resetPostsList() {
        _state.value = _state.value.copy(
            isLoading = true,
            emptyState = false,
            postsList = emptyList()
        )
    }

    private fun appendPosts(newPostsList: List<Post>) {
        val updatedList = _state.value.postsList.toMutableList()
        updatedList.addAll(newPostsList)
        _state.value = _state.value.copy(
            isLoading = false,
            postsList = updatedList
        )
    }

    private fun setEmptyState() {
        _state.value = _state.value.copy(
            isLoading = false,
            emptyState = true,
        )
    }

    private fun incrementPage() {
        page++
    }

    private fun resetPage() {
        page = DEFAULT_PAGE_NUMBER
    }
}
