package com.example.myapp.presentation.screen.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapp.presentation.screen.feed.FeedScreen
import com.example.myapp.presentation.screen.home.HomeScreen
import com.example.myapp.presentation.screen.register_second.RegistrationSecondScreen
import com.example.myapp.presentation.screen.login.LoginScreen
import com.example.myapp.presentation.screen.register_first.RegistrationFirstScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                onLoginClick = { navController.navigate(Routes.LOGIN) },
                onRegisterClick = { navController.navigate(Routes.REG_1) }
            )
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                onBack = { navController.popBackStack() },
                onLogin = { navController.navigate(Routes.FEED)}
            )
        }

        composable(Routes.REG_1) {
            RegistrationFirstScreen(
                onBack = { navController.popBackStack() },
                onNext = { navController.navigate(Routes.REG_2) }
            )
        }

        composable(Routes.REG_2) {
            RegistrationSecondScreen(
                onBack = { navController.popBackStack() },
                onFinish = {  navController.navigate(Routes.FEED) }
            )
        }

        composable(Routes.FEED){
            FeedScreen()
        }
    }
}




object Routes {
    const val HOME = "home"
    const val LOGIN = "login"
    const val REG_1 = "reg_1"
    const val REG_2 = "reg_2"
    const val FEED = "feed"
}
