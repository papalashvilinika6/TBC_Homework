package com.example.myapp.presentation.screen.theme

import AppTypography
import LocalAppTypography
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*


@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkAppColors else LightAppColors
    val snackbarHostState = remember { SnackbarHostState() }

    CompositionLocalProvider(
        LocalAppColors provides colors,
        LocalAppTypography provides AppTypography,
        LocalSnackbarHostState provides snackbarHostState
    ) {
        content()
    }
}


object AppThemeProvider {
    val colors: AppColors
        @Composable get() = LocalAppColors.current
    val typography: Typography
        @Composable get() = LocalAppTypography.current
}

