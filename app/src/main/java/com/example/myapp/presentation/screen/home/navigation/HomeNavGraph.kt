package com.example.myapp.presentation.screen.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.myapp.presentation.screen.home.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

fun NavGraphBuilder.homeNavGraph() {
    composable<HomeRoute> {
        HomeScreen()
    }
}