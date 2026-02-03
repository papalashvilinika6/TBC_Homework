package com.example.myapp.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

val LocalSnackbarHostState = staticCompositionLocalOf<SnackbarHostState> {
    error("SnackbarHostState not provided")
}

object AppThemeProvider {
    val colors @Composable get() = LocalAppColors.current
    val typography @Composable get() = LocalAppTypography.current
}

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    val isDark = isSystemInDarkTheme()
    val colors = if (isDark) DarkColors else LightColors
    val snackbarHostState = remember { SnackbarHostState() }

    CompositionLocalProvider(
        LocalAppColors provides colors,
        LocalAppTypography provides DefaultTypography,
        LocalSnackbarHostState provides snackbarHostState
    ) {
        content()
    }
}
