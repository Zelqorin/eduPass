package com.edupass.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

private val LightColorScheme = lightColorScheme(
    primary = EduPassBlue,
    onPrimary = EduPassTextOnPrimary,
    primaryContainer = EduPassBlueLight,
    onPrimaryContainer = EduPassTextPrimary,
    secondary = EduPassBlueDark,
    onSecondary = EduPassTextOnPrimary,
    background = EduPassScreenBg,
    onBackground = EduPassTextPrimary,
    surface = EduPassWhite,
    onSurface = EduPassTextPrimary,
    surfaceVariant = EduPassScreenBg,
    onSurfaceVariant = EduPassTextSecondary,
    outline = EduPassTextSecondary
)

private val DarkColorScheme = darkColorScheme(
    primary = EduPassBlueDarkTheme,
    onPrimary = EduPassTextPrimary,
    primaryContainer = EduPassBlueDark,
    onPrimaryContainer = EduPassTextPrimaryDark,
    secondary = EduPassBlueLight,
    onSecondary = EduPassTextPrimary,
    background = EduPassBackgroundDark,
    onBackground = EduPassTextPrimaryDark,
    surface = EduPassSurfaceDark,
    onSurface = EduPassTextPrimaryDark,
    surfaceVariant = EduPassSurfaceDark,
    onSurfaceVariant = EduPassTextSecondaryDark,
    outline = EduPassTextSecondaryDark
)

private val EduPassShapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp)
)

@Composable
fun EduPassTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = EduPassShapes,
        content = content
    )
}