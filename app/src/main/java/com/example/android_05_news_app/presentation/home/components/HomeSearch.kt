package com.example.android_05_news_app.presentation.home

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import com.example.android_05_news_app.R
import com.example.android_05_news_app.presentation.modifiers.interceptKey

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeSearch(
    modifier: Modifier = Modifier,
    searchInput: String = "",
    onSearchInputChanged: (String) -> Unit,
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = searchInput,
        onValueChange = onSearchInputChanged,
        placeholder = { Text(stringResource(R.string.home_search)) },
        leadingIcon = { Icon(Icons.Filled.Search, null) },
        modifier = modifier
            .fillMaxWidth()
            .interceptKey(Key.Enter) {
                submitSearch(onSearchInputChanged, context)
                keyboardController?.hide()
                focusManager.clearFocus(force = true)
            },
    )
}

private fun submitSearch(
    onSearchInputChanged: (String) -> Unit,
    context: Context
) {
    onSearchInputChanged("")
    Toast.makeText(
        context,
        "Search is not yet implemented",
        Toast.LENGTH_SHORT
    ).show()
}