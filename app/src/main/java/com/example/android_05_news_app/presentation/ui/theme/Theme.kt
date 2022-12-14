package com.example.android_05_news_app.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = DeepOrange500,
    primaryContainer = DeepOrange200,
    onPrimary = Color.White,
    secondary = DeepOrange200,
    secondaryContainer = DeepOrange700,
    onSecondary = Color.White,
    error = RedErrorDark,
    onError = RedErrorLight,
    background = Grey2,
    onBackground = Grey1,
    surface = Color.White,
    onSurface = Black2,
)

private val DarkColors = darkColorScheme(
    primary = DeepOrange700,
    primaryContainer = Color.White,
    onPrimary = Color.White,
    secondary = Black1,
    onSecondary = Color.White,
    secondaryContainer = DeepOrange500,
    error = RedErrorLight,
    background = Grey4,
    onBackground = Grey3,
    surface = Black1,
    onSurface = Color.White,
)

@Composable
fun NewsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColors
    } else {
        LightColors
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = NewsTypography,
        shapes = NewsShapes,
        content = content
    )
}