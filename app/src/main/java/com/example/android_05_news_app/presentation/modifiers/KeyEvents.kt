package com.example.android_05_news_app.presentation.modifiers

import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.*

fun Modifier.interceptKey(key: Key, onKeyEvent: () -> Unit): Modifier {
    return this.onPreviewKeyEvent {
        if (it.key == key && it.type == KeyEventType.KeyUp) {
            onKeyEvent()
            true
        } else it.key == key
    }
}