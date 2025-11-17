package com.example.urstore.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.urstore.presentation.cart.CartScreen
import com.example.urstore.presentation.home.HomeScreen
import com.example.urstore.presentation.order.OrderScreen
import com.example.urstore.presentation.profile.ProfileScreen
import com.example.urstore.presentation.splash.SplashScreen
import com.example.urstore.presentation.wishlist.WishlistScreen

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

        composable(route = Screen.CART_SCREEN.route) {
            CartScreen()
        }

        composable(route = Screen.WISHLIST_SCREEN.route) {
            WishlistScreen()
        }

        composable(route = Screen.ORDER_SCREEN.route) {
            OrderScreen()
        }

        composable(route = Screen.PROFILE_SCREEN.route) {
            ProfileScreen()
        }
    }
}