package com.droidcon.uganda.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Colors from uganda.droidcon.com
val DroidConBlue = Color(0xFF0055FF)
val DroidConGreen = Color(0xFF00FF4F)
val DroidConMint = Color(0xFFD8FFD4)
val DroidConCyan = Color(0xFF28F4EB)
val DroidConNavy = Color(0xFF112F5B)

private val SiteColorScheme = lightColorScheme(
    primary = DroidConBlue,
    onPrimary = Color.White,
    primaryContainer = DroidConBlue,
    onPrimaryContainer = Color.White,
    secondary = DroidConNavy,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE6EAF3),
    onSecondaryContainer = DroidConNavy,
    tertiary = DroidConGreen,
    onTertiary = Color.Black,
    tertiaryContainer = DroidConMint,
    onTertiaryContainer = Color.Black,
    background = Color.White,
    surface = Color.White,
    onSurface = Color.Black,
    onSurfaceVariant = Color(0xFF6E6E6E),
    surfaceVariant = Color(0xFFF5F5F5)
)

@Composable
fun DroidConTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = SiteColorScheme,
        typography = MaterialTheme.typography,
        content = content
    )
}
