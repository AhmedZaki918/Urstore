package com.example.urstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.urstore.presentation.navigation.NavGraph
import com.example.urstore.presentation.navigation.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(),   // Force Light Theme
            ) {
                MainUi()
            }
        }
    }
}

@Composable
fun MainUi() {
    val navController = rememberNavController()
    val startDestination = Screen.SPLASH_SCREEN.route
    NavGraph(
        currentDest = startDestination,
        navController = navController
    )
}

