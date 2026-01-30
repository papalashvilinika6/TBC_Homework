package com.example.myapp.presentation.screen.theme

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.staticCompositionLocalOf

val LocalSnackbarHostState = staticCompositionLocalOf<SnackbarHostState> {
    error("LocalSnackbarHostState not provided")
}