package com.example.myapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.ui.Modifier
import com.example.myapp.presentation.screen.register.RegisterScreen
import com.example.myapp.presentation.screen.theme.AppTheme
import com.example.myapp.presentation.screen.theme.LocalSnackbarHostState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val snackbarHostState = LocalSnackbarHostState.current

                Scaffold(
                    snackbarHost = {
                        SnackbarHost(
                            modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
                            hostState = snackbarHostState,
                        )
                    },
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        RegisterScreen()
                    }
                }
            }
        }
    }
}
