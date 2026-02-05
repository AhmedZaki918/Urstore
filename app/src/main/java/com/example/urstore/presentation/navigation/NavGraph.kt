package com.example.urstore.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.urstore.presentation.cart.CartScreen
import com.example.urstore.presentation.details.DetailsScreen
import com.example.urstore.presentation.details.DetailsViewModel
import com.example.urstore.presentation.home.HomeScreen
import com.example.urstore.presentation.order.OrderScreen
import com.example.urstore.presentation.profile.ProfileScreen
import com.example.urstore.presentation.see_all.SeeAllScreen
import com.example.urstore.presentation.splash.SplashScreen
import com.example.urstore.presentation.wishlist.WishlistScreen
import com.example.urstore.util.ProductSharedViewModel

@Composable
fun NavGraph(
    productSharedViewModel: ProductSharedViewModel = hiltViewModel(),
    currentDest: String,
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = currentDest
    ) {
        composable(route = Screen.HOME_SCREEN.route) {
            HomeScreen(
                productSharedViewModel = productSharedViewModel,
                navController = navController
            )
        }

        composable(route = Screen.SPLASH_SCREEN.route) {
            SplashScreen(navController = navController)
        }

        composable(route = Screen.CART_SCREEN.route) {
            CartScreen(navController = navController)
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

        composable(route = Screen.SEE_ALL_SCREEN.route) {
            SeeAllScreen(navController = navController)
        }

        composable(
            route = Screen.DETAIL_SCREEN.route
        ) {
            val viewModel: DetailsViewModel = hiltViewModel()
            DetailsScreen(
                productSharedViewModel = productSharedViewModel,
                detailsViewModel = viewModel,
                navController = navController
            )
        }
    }
}