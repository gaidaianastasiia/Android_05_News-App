package com.example.android_05_news_app.presentation.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material.MaterialTheme
import androidx.compose.material.*
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import com.example.android_05_news_app.domain.model.NewsCategories
import com.example.android_05_news_app.presentation.components.CircularProgress
import com.example.android_05_news_app.presentation.home.components.HomeAppBar
import com.example.android_05_news_app.presentation.home.components.HomeEmptyState
import com.example.android_05_news_app.presentation.home.components.PostsList

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreenContent(
    state: HomeState,
    onSearchInputChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
    onSelectedCategoryChanged: (NewsCategories) -> Unit,
    OnScrollPostsListListener: (Int) -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    val focusManager = LocalFocusManager.current

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .background(color = MaterialTheme.colors.surface)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
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
        if (state.postsList.isNotEmpty()) {
            PostsList(
                postsList = state.postsList,
                onScrollListener = OnScrollPostsListListener
            )
        }
    }
}




