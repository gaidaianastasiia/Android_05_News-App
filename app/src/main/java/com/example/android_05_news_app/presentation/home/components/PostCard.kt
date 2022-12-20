package com.example.android_05_news_app.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_05_news_app.domain.model.Post
import com.example.android_05_news_app.utils.DEFAULT_NEWS_IMAGE
import com.example.android_05_news_app.utils.loadPicture
import com.example.android_05_news_app.R


@Composable
fun PostCard(
    post: Post,
    onPostClick: (post: Post) -> Unit,
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable(
                onClick = { onPostClick(post) }
            ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .padding(end = 8.dp)
            ) {
                val image =
                    loadPicture(url = post.urlToImage,
                        defaultImage = DEFAULT_NEWS_IMAGE).value
                image?.let { img ->
                    Image(
                        bitmap = img.asImageBitmap(),
                        contentDescription = stringResource(R.string.post_image_description),
                        modifier = Modifier
                            .fillMaxHeight(),
                        contentScale = ContentScale.Fit,
                    )
                }
            }
            Column() {
                Text(
                    text = post.title ?: stringResource(R.string.post_default_title),
                    fontSize = 16.sp
                )
            }
        }


    }
}
