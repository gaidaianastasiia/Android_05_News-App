package com.example.android_05_news_app.presentation.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.MaterialTheme
import androidx.compose.material.*
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.android_05_news_app.domain.model.NewsCategories
import com.example.android_05_news_app.presentation.components.CircularProgress
import com.example.android_05_news_app.presentation.components.PostCards
import com.example.android_05_news_app.presentation.components.SearchAppBar
import com.example.android_05_news_app.presentation.model.Category

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreenContent(
    state: HomeState,
    onSelectedCategoryChanged: (NewsCategories) -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .background(color = MaterialTheme.colors.surface),
        topBar = {
            SearchAppBar(
                query = "",
                categoriesList = state.categoriesList ?: emptyList(),
                onSearchInputChanged = {},
                onSelectedCategoryChanged = onSelectedCategoryChanged
            )
        },
    ) {
        if (state.isLoading) {
            CircularProgress()
        }
        if (state.postsList != null) {
            PostCards(postsList = state.postsList)
        }
    }
}



