package com.example.android_05_news_app.presentation.post

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import com.example.android_05_news_app.domain.model.Post
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_05_news_app.R
import com.example.android_05_news_app.presentation.ui.theme.Blue800
import com.example.android_05_news_app.presentation.ui.theme.NewsTheme
import com.example.android_05_news_app.presentation.utils.DEFAULT_NEWS_IMAGE
import com.example.android_05_news_app.presentation.utils.loadPicture

private val DEFAULT_SPACER_SIZE = 8.dp
private const val POST_SOURCE_LINK_STRING_ANNOTATION_TAG = "URL"

@Composable
fun PostScreen(
    state: PostState,
    onBack: () -> Unit,
    onGoToPostSourceClick: (String) -> Unit,
) {
    NewsTheme {
        PostScreenContent(
            post = state.post,
            onBack = onBack,
            onGoToPostSourceClick = onGoToPostSourceClick,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PostScreenContent(
    post: Post,
    onBack: () -> Unit,
    onGoToPostSourceClick: (String) -> Unit,
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)

    Scaffold(
        topBar = {
            TopAppBar(
                onBack = onBack,
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->
        val state = rememberScrollState()
        PostContent(
            post = post,
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .padding(innerPadding)
                .verticalScroll(state),
            onGoToPostSourceClick = onGoToPostSourceClick,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar(
    onBack: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior?,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_title),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.post_navigation_icon_description),
                    tint = MaterialTheme.colorScheme.onPrimary)
            }
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
    )
}

@Composable
private fun PostContent(
    post: Post,
    modifier: Modifier = Modifier,
    onGoToPostSourceClick: (String) -> Unit,
) {
    Column(modifier = modifier.padding(DEFAULT_SPACER_SIZE)) {
        PostHeaderImage(post)
        Spacer(Modifier.height(DEFAULT_SPACER_SIZE))
        PostTitle(post)
        Spacer(Modifier.height(DEFAULT_SPACER_SIZE))
        PostSubtitle(post)
        PostMetadata(post, Modifier.padding(bottom = 24.dp))
        PostContent(post)
        Spacer(Modifier.height(DEFAULT_SPACER_SIZE))
        PostSource(post, onGoToPostSourceClick)
    }
}

@Composable
private fun PostHeaderImage(post: Post) {
    val image = loadPicture(url = post.urlToImage, defaultImage = DEFAULT_NEWS_IMAGE).value

    val imageModifier = Modifier
        .heightIn(min = 180.dp)
        .fillMaxWidth()
        .clip(shape = MaterialTheme.shapes.medium)

    image?.let { img ->
        Image(bitmap = img.asImageBitmap(),
            contentDescription = stringResource(R.string.post_image_description),
            modifier = imageModifier,
            contentScale = ContentScale.Crop)
    }
}

@Composable
private fun PostTitle(post: Post) {
    Text(text = post.title ?: stringResource(R.string.post_default_title),
        style = MaterialTheme.typography.headlineMedium)
}

@Composable
private fun PostSubtitle(post: Post) {
    post.description?.let { description ->
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(Modifier.height(DEFAULT_SPACER_SIZE))
    }
}

@Composable
private fun PostMetadata(
    post: Post,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.semantics(mergeDescendants = true) {},
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            imageVector = Icons.Filled.AccountCircle,
            contentDescription = null,
            modifier = Modifier.size(40.dp),
            colorFilter = ColorFilter.tint(LocalContentColor.current),
            contentScale = ContentScale.Fit,
        )
        Spacer(Modifier.width(DEFAULT_SPACER_SIZE))
        Text(
            text = post.author ?: stringResource(R.string.post_default_author),
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
        )
    }
}

@Composable
private fun PostContent(post: Post) {
    Text(
        text = post.content ?: stringResource(R.string.post_default_content),
        style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 28.sp),
    )
}

@Composable
private fun PostSource(
    post: Post,
    onGoToPostSourceClick: (String) -> Unit,
) {
    post.url?.let { url ->
        val postSourceLinkText = buildAnnotatedString {
            val str = stringResource(R.string.post_source_link_text)
            append(str)
            addStringAnnotation(tag = POST_SOURCE_LINK_STRING_ANNOTATION_TAG,
                annotation = url,
                start = 0,
                end = str.length)
        }

        ClickableText(style = MaterialTheme.typography.labelLarge.copy(
            color = Blue800,
            textDecoration = TextDecoration.Underline,
        ), text = postSourceLinkText, onClick = {
            postSourceLinkText.getStringAnnotations(POST_SOURCE_LINK_STRING_ANNOTATION_TAG, it, it)
                .firstOrNull()?.let { stringAnnotation ->
                    onGoToPostSourceClick(stringAnnotation.item)
                }
        })
    }
}
