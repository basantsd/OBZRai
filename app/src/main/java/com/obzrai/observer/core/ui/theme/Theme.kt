package com.obzrai.observer.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightScheme = lightColorScheme(
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

@Composable
fun AIObserverTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightScheme,
        typography = ObserverTypography,
        content = content,
    )
}
