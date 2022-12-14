package com.example.android_05_news_app.presentation.home.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.ImeAction
import com.example.android_05_news_app.R
import com.example.android_05_news_app.domain.model.NewsCategories
import com.example.android_05_news_app.presentation.components.NewsCategoryChip
import com.example.android_05_news_app.presentation.model.Category
import com.google.accompanist.flowlayout.FlowRow

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeAppBar(
    searchInput: String = "",
    onSearchInputChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
    categoriesList: List<Category>,
    onSelectedCategoryChanged: (NewsCategories) -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        elevation = 8.dp,
    ) {
        Column {

            Row(modifier = Modifier.fillMaxWidth()) {
                val keyboardController = LocalSoftwareKeyboardController.current
                val focusManager = LocalFocusManager.current

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, top = 8.dp),
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