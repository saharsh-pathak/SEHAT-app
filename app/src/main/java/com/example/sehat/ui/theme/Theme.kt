package com.example.sehat.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = MaroonPrimary,
    onPrimary = OnMaroonPrimary,
    primaryContainer = MaroonContainer,
    onPrimaryContainer = MaroonPrimaryDark,
    secondary = MaroonPrimaryDark,
    onSecondary = Color.White,
    background = CreamBackground,
    onBackground = TextPrimary,
    surface = Color.White,
    onSurface = TextPrimary,
    surfaceVariant = CreamSurface,
    onSurfaceVariant = TextSecondary,
    outline = SurfaceVariant
)

@Composable
fun SehatTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
