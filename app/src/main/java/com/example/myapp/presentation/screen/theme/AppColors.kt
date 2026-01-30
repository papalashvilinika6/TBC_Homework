package com.example.myapp.presentation.screen.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

val LightColors = lightColorScheme(
    primary = Color(0xFF4EE6A8),
    background = Color(0xFFF7F7F7),
    surface = Color(0xFFFFFFFF),
    onBackground = Color(0xFF111111),
    onSurface = Color(0xFF111111),
)

val DarkColors = darkColorScheme(
    primary = Color(0xFF4EE6A8),
    background = Color(0xFF1C2A32),
    surface = Color(0xFF2A3B45),
    onBackground = Color(0xFFFFFFFF),
    onSurface = Color(0xFFFFFFFF),
)

val white = Color(0xFFFFFFFF)
val error = Color(0xFFFF6B6B)
val errorBackground = Color(0xFF2A3B45)
val loader = Color(0xFF4EE6A8)

data class AppColors(
    val primary: Color,
    val background: Color,
    val surface: Color,
    val onBackground: Color,
    val onSurface: Color,
    val divider: Color,
    val muted: Color,
    val like: Color,
    val iconInactive: Color,
)

val LightAppColors = AppColors(
    primary = LightColors.primary,
    background = LightColors.background,
    surface = LightColors.surface,
    onBackground = LightColors.onBackground,
    onSurface = LightColors.onSurface,
    divider = Color(0x22000000),
    muted = Color(0x99111111),
    like = Color(0xFF4EE6A8),
    iconInactive = Color(0x99111111),
)

val DarkAppColors = AppColors(
    primary = DarkColors.primary,
    background = DarkColors.background,
    surface = DarkColors.surface,
    onBackground = DarkColors.onBackground,
    onSurface = DarkColors.onSurface,
    divider = Color(0x33FFFFFF),
    muted = Color(0xCCFFFFFF),
    like = Color(0xFF4EE6A8),
    iconInactive = Color(0xCCFFFFFF),
)

val LocalAppColors = staticCompositionLocalOf { LightAppColors }