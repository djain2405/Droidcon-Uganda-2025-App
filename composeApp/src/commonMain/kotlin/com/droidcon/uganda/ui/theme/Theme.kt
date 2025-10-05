package com.droidcon.uganda.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// DroidCon Uganda brand colors
val DroidConGreen = Color(0xFF4CAF50)
val DroidConDarkGreen = Color(0xFF388E3C)
val DroidConLightGreen = Color(0xFF81C784)
val DroidConAccent = Color(0xFFFFEB3B)
val DroidConOrange = Color(0xFFFF9800)

private val LightColorScheme = lightColorScheme(
    primary = DroidConGreen,
    onPrimary = Color.White,
    primaryContainer = DroidConLightGreen,
    onPrimaryContainer = DroidConDarkGreen,
    secondary = DroidConOrange,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFFE0B2),
    onSecondaryContainer = Color(0xFFE65100),
    tertiary = DroidConAccent,
    background = Color(0xFFFAFAFA),
    surface = Color.White,
    onSurface = Color(0xFF212121),
    surfaceVariant = Color(0xFFF5F5F5)
)

private val DarkColorScheme = darkColorScheme(
    primary = DroidConLightGreen,
    onPrimary = Color(0xFF1B5E20),
    primaryContainer = DroidConDarkGreen,
    onPrimaryContainer = DroidConLightGreen,
    secondary = DroidConOrange,
    onSecondary = Color(0xFF4E2600),
    secondaryContainer = Color(0xFFE65100),
    onSecondaryContainer = Color(0xFFFFCC80),
    tertiary = DroidConAccent,
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
