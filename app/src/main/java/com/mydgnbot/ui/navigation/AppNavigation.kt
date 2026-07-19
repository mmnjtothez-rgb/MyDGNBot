package com.mydgnbot.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mydgnbot.ui.screens.HomeScreen
import com.mydgnbot.ui.screens.SettingsScreen

sealed class Screen(val route: String) {

    data object Home : Screen("home")

    data object Settings : Screen("settings")
}

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {

        composable(Screen.Home.route) {

            HomeScreen(
                onSettingsClick = {
                    navController.navigate(Screen.Settings.route)
                }
            )

        }

        composable(Screen.Settings.route) {

            SettingsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )

        }
    }
}
