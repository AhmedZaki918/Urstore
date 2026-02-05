package com.example.urstore

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.urstore.data.local.Constants.PRODUCT_ID
import com.example.urstore.presentation.navigation.BottomBarScreen
import com.example.urstore.presentation.navigation.NavGraph
import com.example.urstore.presentation.navigation.Screen
import com.example.urstore.ui.theme.Black
import com.example.urstore.ui.theme.Brown
import com.example.urstore.ui.theme.Dark_Yellow
import com.example.urstore.ui.theme.White
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ForceLightSystemBars()
            MainUi()
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainUi() {
    val navController = rememberNavController()
    //val startDestination = Screen.SPLASH_SCREEN.route
    val startDestination = Screen.HOME_SCREEN.route

    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) {
        NavGraph(
            currentDest = startDestination,
            navController = navController
        )
    }
}


@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Cart,
        BottomBarScreen.Wishlist,
        BottomBarScreen.MyOrder,
        BottomBarScreen.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    var bottomBarState by rememberSaveable { (mutableStateOf(true)) }

    bottomBarState = when (currentDestination?.route) {
        Screen.SPLASH_SCREEN.route -> false
        Screen.DETAIL_SCREEN.route -> false
        Screen.SEE_ALL_SCREEN.route -> false
        Screen.CART_SCREEN.route -> false
        else -> true
    }


    AnimatedVisibility(
        visible = bottomBarState,
        content = {
            NavigationBar(
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .height(72.dp),
                containerColor = White,
                contentColor = Black,
            ) {
                screens.forEach { screen ->
                    AddItem(
                        screen = screen,
                        currentDestination = currentDestination,
                        navController = navController
                    )
                }
            }
        })
}


@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    NavigationBarItem(
        label = {
            Text(
                text = when (screen.route) {
                    BottomBarScreen.Home.route -> stringResource(R.string.explore)
                    BottomBarScreen.Cart.route -> stringResource(R.string.cart)
                    BottomBarScreen.Wishlist.route -> stringResource(R.string.wishlist)
                    BottomBarScreen.MyOrder.route -> stringResource(R.string.my_order)
                    else -> stringResource(R.string.profile)
                }
            )
        },
        alwaysShowLabel = true,
        icon = {
            Icon(
                painter = painterResource(id = screen.icon),
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Brown,
            selectedTextColor = Brown,
            indicatorColor = Dark_Yellow
        )
    )
}


@Composable
fun ForceLightSystemBars() {
    val view = LocalView.current
    val window = (view.context as Activity).window

    SideEffect {
        val controller = WindowCompat.getInsetsController(window, view)
        controller.isAppearanceLightStatusBars = true
        controller.isAppearanceLightNavigationBars = true
    }
}
