package com.example.urstore.presentation.navigation

import com.example.urstore.R

sealed class BottomBarScreen(
    val route: String,

    val icon: Int
) {
    data object Home : BottomBarScreen(
        route = Screen.HOME_SCREEN.route,
        icon = R.drawable.btn_1
    )

    data object Cart : BottomBarScreen(
        route = Screen.CART_SCREEN.route,
        icon = R.drawable.btn_2
    )

    data object Wishlist : BottomBarScreen(
        route = Screen.WISHLIST_SCREEN.route,
        icon = R.drawable.btn_3
    )

    data object MyOrder : BottomBarScreen(
        route = Screen.ORDER_SCREEN.route,
        icon = R.drawable.btn_4
    )

    data object Profile : BottomBarScreen(
        route = Screen.PROFILE_SCREEN.route,
        icon = R.drawable.btn_5
    )
}