package com.practice.rickyandmorty.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Black = Color(0xFF0B0B0B)
val DarkGray = Color(0xFF121212)
val CardBackground = Color(0xFF1C1C1E)

val White = Color(0xFFFFFFFF)
val GrayLight = Color(0xFFB3B3B3)
val GrayMedium = Color(0xFF8A8A8A)
val Black800 = Color(0xFF424242)

val BackgroundBrush = Brush.verticalGradient(
    colors = listOf(
        Black,
        DarkGray,
        Black800
    )
)

val ImageOverlay = Brush.verticalGradient(
    colors = listOf(
        Color.Transparent,
        Color.Black.copy(alpha = 0.6f),
        Color.Black
    )
)