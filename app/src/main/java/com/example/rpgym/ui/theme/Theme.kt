package com.example.rpgym.ui.theme

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val DarkColors = darkColorScheme(
    primary = RpgGold,
    onPrimary = Black,
    secondary = RpgGreen,
    background = RpgSurface,
    onBackground = White,
    surface = RpgSurfaceVariant,
    onSurface = White,
    error = RpgRed,
)

private val LightColors = lightColorScheme(
    primary = RpgGold,
    secondary = RpgGreen,
    error = RpgRed,
)

@Composable
fun RPGymTheme(
    // The game art is dark-themed, so always use the dark scheme (ignore the system
    // setting) — this keeps all default text/icon colors bright and readable.
    darkTheme: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
    ) {
        // Default bare Text/Icon to a bright color. Without this, content drawn over
        // transparent containers (e.g. our background-image screens) falls back to black.
        CompositionLocalProvider(LocalContentColor provides colorScheme.onBackground, content = content)
    }
}
