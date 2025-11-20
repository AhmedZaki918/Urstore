package com.example.urstore.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.urstore.data.local.Constants.PRODUCT_ID
import com.example.urstore.presentation.cart.CartScreen
import com.example.urstore.presentation.details.DetailsIntent
import com.example.urstore.presentation.details.DetailsScreen
import com.example.urstore.presentation.details.DetailsViewModel
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
            HomeScreen(navController = navController)
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


        composable(
            route = "${Screen.DETAIL_SCREEN.route}/{$PRODUCT_ID}",
            arguments = listOf(
                navArgument(name = PRODUCT_ID) {
                    type = NavType.IntType
                }
            )
        ) {
            val viewModel: DetailsViewModel = hiltViewModel()
            val productId = it.arguments?.getInt(PRODUCT_ID)

            if (productId != null) {
                LaunchedEffect(true) {
                    viewModel.onIntent(DetailsIntent.DisplayProductDetails(productId))
                }
            }

            DetailsScreen(
                viewModel = viewModel,
                navController = navController,
            )
        }
    }
}