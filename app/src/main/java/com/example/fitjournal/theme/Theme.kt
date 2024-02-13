package com.example.fitjournal.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColorScheme(
    primary = BlueSecondary,
    secondary = BlueLight,
    tertiary = BlueVariant,
    onPrimary = LightGray,
    onSecondary = DarkGray,
    onTertiary = MediumGray
)

private val LightColorPalette = lightColorScheme(
    primary = BluePrimary,
    secondary = BlueVariant,
    tertiary = BlueLight,
    onPrimary = DarkGray2,
    onSecondary = LightGray,
    onTertiary = DarkGray
)
    /* Other default colors to override
background = Color(0xFFFFFBFE),
surface = Color(0xFFFFFBFE),
onPrimary = Color.White,
onSecondary = Color.White,
onTertiary = Color.White,
onBackground = Color(0xFF1C1B1F),
onSurface = Color(0xFF1C1B1F),
*/

@Composable
fun FitJournalTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
