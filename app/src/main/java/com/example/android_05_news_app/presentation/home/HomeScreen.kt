package com.example.android_05_news_app.presentation.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.material.MaterialTheme
import androidx.compose.material.*
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.android_05_news_app.domain.model.NewsCategories
import com.example.android_05_news_app.presentation.components.CircularProgress
import com.example.android_05_news_app.presentation.home.components.HomeAppBar
import com.example.android_05_news_app.presentation.components.PostCards
import com.example.android_05_news_app.presentation.home.components.HomeEmptyState

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreenContent(
    state: HomeState,
    onSearchInputChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
    onSelectedCategoryChanged: (NewsCategories) -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .background(color = MaterialTheme.colors.surface),
        topBar = {
            HomeAppBar(
                searchInput = state.searchInput,
                onSearchInputChanged = onSearchInputChanged,
                onExecuteSearch = onExecuteSearch,
                categoriesList = state.categoriesList,
                onSelectedCategoryChanged = onSelectedCategoryChanged
            )
        },
    ) {
        if (state.isLoading) {
            CircularProgress()
        }
        if (state.emptyState) {
            HomeEmptyState()
        }
        if (state.postsList != null) {
            PostCards(postsList = state.postsList)
        }
    }
}




