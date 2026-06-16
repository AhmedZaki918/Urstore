package com.example.urstore.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.urstore.data.local.Constants.EMAIL_ADDRESS
import com.example.urstore.data.local.Constants.OTP
import com.example.urstore.presentation.auth.login.LoginScreen
import com.example.urstore.presentation.auth.signup.SignupScreen
import com.example.urstore.presentation.auth.user_updare.EditProfileScreen
import com.example.urstore.presentation.cart.CartScreen
import com.example.urstore.presentation.checkout.CheckoutScreen
import com.example.urstore.presentation.details.DetailsScreen
import com.example.urstore.presentation.details.DetailsViewModel
import com.example.urstore.presentation.home.HomeScreen
import com.example.urstore.presentation.order.details.OrderDetails
import com.example.urstore.presentation.order.orders.OrderScreen
import com.example.urstore.presentation.password_reset.enter_code.EnterCodeScreen
import com.example.urstore.presentation.password_reset.forget_password.ForgotPasswordScreen
import com.example.urstore.presentation.password_reset.reset_password.ResetPasswordScreen
import com.example.urstore.presentation.profile.ProfileScreen
import com.example.urstore.presentation.search.SearchScreen
import com.example.urstore.presentation.see_all.SeeAllScreen
import com.example.urstore.presentation.wishlist.WishlistScreen
import com.example.urstore.util.CartSharedViewModel
import com.example.urstore.util.ProductSharedViewModel

@Composable
fun NavGraph(
    productSharedViewModel: ProductSharedViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val cartSharedViewModel: CartSharedViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.HOME_SCREEN.route
    ) {
        composable(route = Screen.LOGIN_SCREEN.route) {
            LoginScreen(navController = navController)
        }

        composable(route = Screen.SEARCH_SCREEN.route) {
            SearchScreen(
                navController = navController,
                productSharedViewModel = productSharedViewModel
            )
        }

        composable(route = Screen.SIGNUP_SCREEN.route) {
            SignupScreen(navController = navController)
        }

        composable(route = Screen.EDIT_PROFILE_SCREEN.route) {
            EditProfileScreen(navController = navController)
        }

        composable(route = Screen.FORGOT_PASSWORD_SCREEN.route) {
            ForgotPasswordScreen(navController = navController)
        }

        composable(
            route = "${Screen.ENTER_CODE_SCREEN.route}/{$EMAIL_ADDRESS}",
            arguments = listOf(
                navArgument(EMAIL_ADDRESS) {
                    type = NavType.StringType
                }
            )
        ) {
            EnterCodeScreen(navController = navController)
        }


        composable(
            route = "${Screen.RESET_PASSWORD_SCREEN.route}/{$EMAIL_ADDRESS}/{$OTP}",
            arguments = listOf(
                navArgument(EMAIL_ADDRESS) {
                    type = NavType.StringType
                },
                navArgument(OTP) {
                    type = NavType.StringType
                }
            )
        ) {
            ResetPasswordScreen(navController = navController)
        }

        composable(route = Screen.HOME_SCREEN.route) {
            HomeScreen(
                productSharedViewModel = productSharedViewModel,
                navController = navController
            )
        }

        composable(route = Screen.CART_SCREEN.route) {
            CartScreen(
                navController = navController,
                cartSharedVM = cartSharedViewModel
            )
        }

        composable(route = Screen.CHECKOUT_SCREEN.route) {
            CheckoutScreen(
                navController = navController,
                cartSharedVM = cartSharedViewModel
            )
        }

        composable(route = Screen.WISHLIST_SCREEN.route) {
            WishlistScreen()
        }

        composable(route = Screen.ORDER_SCREEN.route) {
            OrderScreen(navController = navController)
        }

        composable(route = Screen.ORDER_DETAILS.route) {
            OrderDetails()
        }

        composable(route = Screen.PROFILE_SCREEN.route) {
            ProfileScreen(navController = navController)
        }

        composable(route = Screen.SEE_ALL_SCREEN.route) {
            SeeAllScreen(
                productSharedViewModel = productSharedViewModel,
                navController = navController,
            )
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