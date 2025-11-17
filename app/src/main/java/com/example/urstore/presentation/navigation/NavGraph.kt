package com.example.urstore.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.urstore.presentation.home.HomeScreen
import com.example.urstore.presentation.splash.SplashScreen

@Composable
fun NavGraph(
    currentDest: String,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = currentDest
    ) {
        composable(route = Screen.HOME_SCREEN.route) {
            HomeScreen()
        }

        composable(route = Screen.SPLASH_SCREEN.route) {
            SplashScreen(navController = navController)
        }
    }
}