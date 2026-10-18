package com.obzrai.observer.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightScheme =
    lightColorScheme(
        primary = ObserverRingBlue,
        onPrimary = Color.White,
        secondary = ObserverRingBlueMuted,
        onSecondary = Color.White,
        tertiary = ObserverSavedGreen,
        background = ObserverSurface,
        onBackground = ObserverOnSurface,
        surface = Color.White,
        onSurface = ObserverOnSurface,
        outline = ObserverOutline,
        error = ObserverRecordingRed,
    )

private val DarkScheme =
    darkColorScheme(
        primary = ObserverRingBlueMuted,
        onPrimary = Color.White,
        secondary = ObserverRingBlue,
        onSecondary = Color.White,
        tertiary = ObserverSavedGreen,
        background = Color(0xFF12171F),
        onBackground = Color(0xFFE8EDF4),
        surface = Color(0xFF1A2332),
        onSurface = Color(0xFFE8EDF4),
        outline = Color(0xFF5A6A7C),
        error = ObserverRecordingRed,
    )

/** FR-075 — follow system dark mode. */
@Composable
fun AIObserverTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkScheme else LightScheme,
        typography = ObserverTypography,
        content = content,
    )
}
