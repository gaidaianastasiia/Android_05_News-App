package com.example.android_05_news_app.presentation.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_05_news_app.R
import com.example.android_05_news_app.domain.model.NewsCategories
import com.example.android_05_news_app.domain.model.Post
import com.example.android_05_news_app.presentation.components.CircularProgress
import com.example.android_05_news_app.presentation.components.EmptyState
import com.example.android_05_news_app.presentation.model.Category
import com.example.android_05_news_app.presentation.ui.theme.NewsTheme
import com.example.android_05_news_app.presentation.utils.DEFAULT_NEWS_IMAGE
import com.example.android_05_news_app.presentation.utils.loadPicture
import com.google.accompanist.flowlayout.FlowRow
import androidx.compose.material3.FilterChip

private val DEFAULT_SPACER_SIZE = 8.dp

@Composable
fun HomeScreen(
    state: HomeState,
    onSearchInputChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
    onSelectedCategoryChanged: (NewsCategories) -> Unit,
    OnScrollPostsListListener: (Int) -> Unit,
    onPostsListItemClick: (post: Post) -> Unit,
) {
    NewsTheme {
        HomeScreenContent(
            state = state,
            onSearchInputChanged = onSearchInputChanged,
            onExecuteSearch = onExecuteSearch,
            onSelectedCategoryChanged = onSelectedCategoryChanged,
            OnScrollPostsListListener = OnScrollPostsListListener,
            onPostsListItemClick = onPostsListItemClick,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
    state: HomeState,
    onSearchInputChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
    onSelectedCategoryChanged: (NewsCategories) -> Unit,
    OnScrollPostsListListener: (Int) -> Unit,
    onPostsListItemClick: (post: Post) -> Unit,
) {
    Scaffold(
        topBar = {
            HomeTopBar(
                searchInput = state.searchInput,
                categoriesList = state.categoriesList,
                onSearchInputChanged = onSearchInputChanged,
                onExecuteSearch = onExecuteSearch,
                onSelectedCategoryChanged = onSelectedCategoryChanged,
            )
        },
    ) { innerPadding ->
        HomeContent(
            isLoading = state.isLoading,
            isEmptyState = state.isEmptyState,
            postsList = state.postsList,
            OnScrollPostsListListener = OnScrollPostsListListener,
            onPostsListItemClick = onPostsListItemClick,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Composable
private fun HomeTopBar(
    searchInput: String,
    categoriesList: List<Category>,
    onSearchInputChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
    onSelectedCategoryChanged: (NewsCategories) -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 16.dp,
        color = MaterialTheme.colorScheme.primary,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = DEFAULT_SPACER_SIZE)
        ) {
            HomeSearch(
                searchInput = searchInput,
                onSearchInputChanged = onSearchInputChanged,
                onExecuteSearch = onExecuteSearch,
            )

            FlowRow {
                categoriesList.forEach { category ->
                    CategoryChip(
                        category = category,
                        onSelectedCategoryChanged = {
                            onSelectedCategoryChanged(it)
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun HomeSearch(
    searchInput: String,
    onSearchInputChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = DEFAULT_SPACER_SIZE),
        value = searchInput,
        singleLine = true,
        onValueChange = { onSearchInputChanged(it) },
        label = { Text(text = stringResource(R.string.home_search)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onExecuteSearch()
                focusManager.clearFocus()
                keyboardController?.hide()
            }
        ),
        leadingIcon = {
            Icon(Icons.Filled.Search,
                contentDescription = "Search Icon")
        },
        textStyle = MaterialTheme.typography.bodyMedium,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoryChip(
    category: Category,
    onSelectedCategoryChanged: (NewsCategories) -> Unit,
) {
    FilterChip(
        selected = category.isSelected,
        label = {
            Text(
                text = category.name.value,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        },
        modifier = Modifier.padding(end = 4.dp),
        onClick = { onSelectedCategoryChanged(category.name) }
    )
}

@Composable
private fun HomeContent(
    isLoading: Boolean,
    isEmptyState: Boolean,
    postsList: List<Post>,
    OnScrollPostsListListener: (Int) -> Unit,
    onPostsListItemClick: (post: Post) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (isLoading) {
        CircularProgress()
    }
    if (isEmptyState) {
        EmptyState()
    }
    if (postsList.isNotEmpty()) {
        PostsList(
            postsList = postsList,
            onScrollListener = OnScrollPostsListListener,
            onPostsListItemClick = onPostsListItemClick,
            modifier = modifier
        )
    }
}

@Composable
private fun PostsList(
    postsList: List<Post>,
    onScrollListener: (Int) -> Unit,
    onPostsListItemClick: (post: Post) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberLazyListState()

    val connection = remember {
        object : NestedScrollConnection {
            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                onScrollListener(scrollState.firstVisibleItemIndex)
                return Velocity.Zero
            }
        }
    }

    LazyColumn(
        modifier = modifier
            .nestedScroll(connection)
            .padding(horizontal = DEFAULT_SPACER_SIZE),
        state = scrollState
    ) {
        items(postsList) { post ->
            PostCard(
                post = post,
                onPostClick = onPostsListItemClick
            )
        }
    }
}

@Composable
private fun PostCard(
    post: Post,
    onPostClick: (post: Post) -> Unit,
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(top = 12.dp)
            .fillMaxWidth()
            .clickable(
                onClick = { onPostClick(post) }
            ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(DEFAULT_SPACER_SIZE)
        ) {
            val image =
                loadPicture(url = post.urlToImage,
                    defaultImage = DEFAULT_NEWS_IMAGE).value
            image?.let { img ->
                Image(
                    bitmap = img.asImageBitmap(),
                    contentDescription = stringResource(R.string.post_image_description),
                    modifier = Modifier
                        .fillMaxWidth(0.3f)
                        .padding(end = DEFAULT_SPACER_SIZE)
                        .fillMaxHeight(),
                    contentScale = ContentScale.Fit,
                )
            }
            Text(
                text = post.title ?: stringResource(R.string.post_default_title),
                style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 20.sp),
            )
        }
    }
}


