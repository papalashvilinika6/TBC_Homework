package com.example.myapp.presentation.screen.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.myapp.presentation.screen.home.navigation.HomeRoute
import com.example.myapp.presentation.screen.home.navigation.homeNavGraph

@Composable
fun NavGraph(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HomeRoute
    ){
        homeNavGraph()
    }
}