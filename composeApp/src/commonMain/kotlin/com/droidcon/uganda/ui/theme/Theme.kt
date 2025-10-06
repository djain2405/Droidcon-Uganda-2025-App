package com.droidcon.uganda.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// DroidCon Uganda brand colors - from uganda.droidcon.com
val DroidConOrange = Color(0xFFFA6E50) // Primary orange from website
val DroidConGreen = Color(0xFF43BC6E) // Primary green from website
val DroidConDarkOrange = Color(0xFFE85A3C) // Darker shade of orange
val DroidConLightOrange = Color(0xFFFFAA95) // Lighter shade of orange
val DroidConDarkGreen = Color(0xFF359956) // Darker shade of green
val DroidConLightGreen = Color(0xFF6DD18F) // Lighter shade of green

private val LightColorScheme = lightColorScheme(
    primary = DroidConOrange,
    onPrimary = Color.White,
    primaryContainer = DroidConLightOrange,
    onPrimaryContainer = DroidConDarkOrange,
    secondary = DroidConGreen,
    onSecondary = Color.White,
    secondaryContainer = DroidConLightGreen,
    onSecondaryContainer = DroidConDarkGreen,
    tertiary = DroidConGreen,
    background = Color(0xFFFAFAFA),
    surface = Color.White,
    onSurface = Color(0xFF212121),
    surfaceVariant = Color(0xFFF5F5F5)
)

private val DarkColorScheme = darkColorScheme(
    primary = DroidConOrange,
    onPrimary = Color(0xFF4E2600),
    primaryContainer = DroidConDarkOrange,
    onPrimaryContainer = DroidConLightOrange,
    secondary = DroidConGreen,
    onSecondary = Color(0xFF1B5E20),
    secondaryContainer = DroidConDarkGreen,
    onSecondaryContainer = DroidConLightGreen,
    tertiary = DroidConLightGreen,
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onSurface = Color(0xFFE0E0E0),
    surfaceVariant = Color(0xFF2C2C2C)
)

@Composable
fun DroidConTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MaterialTheme.typography,
        content = content
    )
}
