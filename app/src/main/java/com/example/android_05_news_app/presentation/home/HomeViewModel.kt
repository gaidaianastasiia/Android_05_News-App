package com.example.android_05_news_app.presentation.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_05_news_app.R
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

object StateKey {
    const val SEARCH_INPUT = "NEWS_STATE_SEARCH_INPUT_KEY"
    const val PAGE = "NEWS_STATE_PAGE_KEY"
    const val CURRENT_CATEGORY = "NEWS_STATE_CURRENT_CATEGORY_KEY"
}

private const val DEFAULT_PAGE_NUMBER = 1
private const val PAGE_SIZE = 20
private const val PAGINATION_OFFSET = 5
private const val EMPTY_SEARCH_INPUT = ""

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPostsFeedInteractor: GetPostsFeedInteractor,
    private val searchPostsInteractor: SearchPostsInteractor,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private var searchRequestJob: Job? = null
    private var currentPage = savedStateHandle[StateKey.PAGE] ?: DEFAULT_PAGE_NUMBER
    private var currentCategory: NewsCategories? =
        savedStateHandle[StateKey.CURRENT_CATEGORY] ?: NewsCategories.GENERAL

    private val _event = MutableSharedFlow<HomeEvent>()
    val event = _event.asSharedFlow()

    private val _state = MutableStateFlow(
        HomeState(
            isLoading = true,
            isEmptyState = false,
            postsList = emptyList(),
            categoriesList = getInitialCategoriesList(),
            searchInput = savedStateHandle[StateKey.SEARCH_INPUT] ?: EMPTY_SEARCH_INPUT,
        )
    )
    val state = _state.asStateFlow()

    init {
        fetchPosts()
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
            is HomeIntent.OnNavigateToPostDetail -> {
                viewModelScope.launch {
                    _event.emit(HomeEvent.NavigateToPostDetail(homeIntent.post))
                }
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

    private fun fetchPosts() {
        val searchInput = _state.value.searchInput

        if (searchInput.isNotEmpty()) {
            updateCategoryList(null)
            searchPosts(searchInput, currentPage)
        } else {
            currentCategory?.let {
                getPosts(it.value, currentPage)
            }
        }
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
                _state.value = _state.value.copy(isLoading = false)
                _event.emit(HomeEvent.ShowError(R.string.home_error_message))
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
                _state.value = _state.value.copy(isLoading = false)
                _event.emit(HomeEvent.ShowError(R.string.home_error_message))
            }
        }
    }

    private fun onScrollPostsList(scrollPosition: Int) {
        if (scrollPosition >= ((PAGE_SIZE * currentPage) - PAGINATION_OFFSET)) {
            setCurrentPage(currentPage + 1)
            val category = currentCategory

            if (category == null) {
                searchPosts(_state.value.searchInput, currentPage)
            } else {
                getPosts(category.value, currentPage)
            }
        }
    }

    private fun onCategoryChanged(category: NewsCategories) {
        resetCurrentPage()
        resetPostsList()
        setSearchInput(EMPTY_SEARCH_INPUT)
        updateCategoryList(category)
        getPosts(category.value, currentPage)
    }

    private fun onSearchInputChanged(searchInput: String) {
        setSearchInput(searchInput)
    }

    private fun onExecuteSearch() {
        val searchInput = _state.value.searchInput

        if (searchInput.isNotEmpty()) {
            resetCurrentPage()
            resetPostsList()
            updateCategoryList(null)
            searchPosts(searchInput, currentPage)
        } else {
            _state.value = _state.value.copy(isLoading = false)
        }
    }

    private fun updateCategoryList(updatedCategory: NewsCategories?) {
        setCurrentCategory(updatedCategory)

        val updatedList = _state.value.categoriesList.map { category ->
            category.copy(isSelected = category.name == updatedCategory)
        }

        _state.value = _state.value.copy(categoriesList = updatedList)
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
            isEmptyState = false,
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
            isEmptyState = true,
        )
    }

    private fun setCurrentPage(page: Int) {
        currentPage = page
        savedStateHandle[StateKey.PAGE] = currentPage
    }

    private fun resetCurrentPage() {
        setCurrentPage(DEFAULT_PAGE_NUMBER)
    }

    private fun setCurrentCategory(category: NewsCategories?) {
        currentCategory = category
        savedStateHandle[StateKey.CURRENT_CATEGORY] = category
    }

    private fun setSearchInput(searchInput: String) {
        _state.value = _state.value.copy(searchInput = searchInput)
        savedStateHandle[StateKey.SEARCH_INPUT] = searchInput
    }
}
