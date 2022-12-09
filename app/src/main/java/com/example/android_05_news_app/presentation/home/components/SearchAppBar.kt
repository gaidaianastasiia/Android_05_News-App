package com.example.android_05_news_app.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.ImeAction
import com.example.android_05_news_app.domain.model.NewsCategories
import com.example.android_05_news_app.presentation.model.Category
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun SearchAppBar(
    query: String,
    categoriesList: List<Category>,
    onSearchInputChanged: (String) -> Unit,
    onSelectedCategoryChanged: (NewsCategories) -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        elevation = 8.dp,
    ) {
        Column {
            Row(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, top = 8.dp),
                    value = query,
                    onValueChange = { /*TODO*/ },
                    label = { Text(text = "Search") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                    ),
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search Icon") },
                    textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                )
            }

            FlowRow(
                modifier = Modifier
                    .padding(start = 8.dp, bottom = 8.dp)
            ) {
                categoriesList.forEach { category ->
                    NewsCategoryChip(
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