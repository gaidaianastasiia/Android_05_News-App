package com.example.android_05_news_app.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.android_05_news_app.domain.model.NewsCategories
import com.example.android_05_news_app.presentation.model.Category
import com.example.android_05_news_app.presentation.ui.theme.Blue300

@Composable
fun NewsCategoryChip(
    category: Category,
    onSelectedCategoryChanged: (NewsCategories) -> Unit,
) {
    Surface(
        modifier = Modifier.padding(top = 8.dp, end = 8.dp),
        elevation = 8.dp,
        shape = MaterialTheme.shapes.medium,
        color = if (category.isSelected) Blue300 else MaterialTheme.colors.primary
    ) {
        Row(modifier = Modifier
            .toggleable(
                value = category.isSelected,
                onValueChange = {
                    onSelectedCategoryChanged(category.name)
                }
            )
        ) {
            Text(
                text = category.name.value,
                style = MaterialTheme.typography.body2,
                color = Color(0xFFFFFFFF),
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}