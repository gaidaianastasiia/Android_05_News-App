package com.example.android_05_news_app.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_05_news_app.domain.model.Post
import com.example.android_05_news_app.utils.DEFAULT_NEWS_IMAGE
import com.example.android_05_news_app.utils.loadPicture

@Composable
fun PostCard(
    post: Post,
    // onClick: () -> Unit,
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable(onClick = { /*TODO*/ }),

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
                        contentDescription = "Recipe Featured Image",
                        modifier = Modifier
                            .fillMaxHeight(),
                        contentScale = ContentScale.Fit,
                    )
                }
            }
            Column() {
                    Text(
                        text = post.title ?: "Title",
                        fontSize = 16.sp
                    )
            }
        }


    }
}

@Composable
@Preview
fun ComposablePreview() {
    PostCard(
        post = Post(
            author = "Adrianna Rodriguez",
            title = "Flu worsens amid COVID, RSV: What to know about symptoms, vaccine - USA TODAY",
            description = "",
            url = "",
            urlToImage = "https://www.gannett-cdn.com/presto/2022/09/28/USAT/f6a9e9e6-6233-41e5-8b0d-db75ea252cfd-GettyImages-1217583991.jpg?auto=webp&crop=2118,1192,x0,y108&format=pjpg&width=1200",
            published_at = "12.12.22",
            content = "Though the “tripledemic” – COVID-19, RSV and influenza –remains a problem in many places, experts say the flu is beginning to hit the country hard.\\r\\nDr. William Schaffner, an infectious disease speci…"
            )
    )

}