package com.practice.rickyandmorty.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Color.White,
    onPrimary = Color.Black,

    secondary = Color(0xFFB0B0B0),
    onSecondary = Color.Black,

    background = Color(0xFF000000),
    onBackground = Color.White,

    surface = Color(0xFF0A0A0A),
    onSurface = Color.White,

    surfaceVariant = Color(0xFF141414),
    onSurfaceVariant = Color(0xFFB3B3B3),

    tertiary = Color(0xFF00E5FF),
    onTertiary = Color.Black,

    error = Color(0xFFCF6679),
    onError = Color.Black
)

private val LightColorScheme = lightColorScheme(
    primary = Color.Black,
    onPrimary = Color.White,

    secondary = Color(0xFF333333),
    onSecondary = Color.White,

    background = Color(0xFFF2F2F2),
    onBackground = Color.Black,

    surface = Color(0xFFFFFFFF),
    onSurface = Color.Black,

    surfaceVariant = Color(0xFFE5E5E5),
    onSurfaceVariant = Color(0xFF4F4F4F),

    tertiary = Color(0xFF007C91),
    onTertiary = Color.White,

    error = Color(0xFFB00020),
    onError = Color.White
)

@Composable
fun RickyAndMortyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    SetStatusBarStyle(darkTheme = darkTheme)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun SetStatusBarStyle(darkTheme: Boolean) {
    val view = LocalView.current

    SideEffect {
        val window = (view.context as Activity).window
        val controller = WindowCompat.getInsetsController(window, view)
        controller.isAppearanceLightStatusBars = !darkTheme
    }
}