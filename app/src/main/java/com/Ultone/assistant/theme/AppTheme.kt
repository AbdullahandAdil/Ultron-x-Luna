package com.ultone.assistant.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val UltronDarkColors = darkColorScheme(
    primary = Color(0xFF00D9FF),
    secondary = Color(0xFFB56CFF),
    background = Color(0xFF080B14),
    surface = Color(0xFF111827),
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun UltronLunaTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = UltronDarkColors,
        content = content
    )
}