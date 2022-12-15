package com.example.android_05_news_app.presentation.home.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.Velocity
import com.example.android_05_news_app.domain.model.Post

@Composable
fun PostsList(
    postsList: List<Post>,
    onScrollListener: (Int) -> Unit,
    onPostsListItemClick: (post: Post) -> Unit
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
        modifier = Modifier.nestedScroll(connection),
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