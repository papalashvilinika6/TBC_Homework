package com.example.myapp.core.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class AppColors(
    val background: Color,
    val surface: Color,
    val onSurface: Color,
    val primary: Color,
    val onPrimary: Color,
    val muted: Color,
    val divider: Color,
    val error: Color
)

val LightColors = AppColors(
    background = Color(0xFFF6F7FB),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF14161A),
    primary = Color(0xFF2F80ED),
    onPrimary = Color(0xFFFFFFFF),
    muted = Color(0xFF7A8291),
    divider = Color(0xFFE7EAF1),
    error = Color(0xFFE53935)
)

val DarkColors = AppColors(
    background = Color(0xFF0F1115),
    surface = Color(0xFF171A21),
    onSurface = Color(0xFFECEFF4),
    primary = Color(0xFF56A0FF),
    onPrimary = Color(0xFF08111F),
    muted = Color(0xFFA6ADBB),
    divider = Color(0xFF232735),
    error = Color(0xFFFF6B6B)
)

val LocalAppColors = staticCompositionLocalOf { LightColors }
