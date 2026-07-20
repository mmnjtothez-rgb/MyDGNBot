package com.mydgnbot.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mydgnbot.data.AppContainer
import com.mydgnbot.ui.screens.HomeScreen
import com.mydgnbot.ui.screens.SettingsScreen
import com.mydgnbot.ui.viewmodel.SettingsViewModel
import com.mydgnbot.ui.viewmodel.SettingsViewModelFactory

sealed class Screen(val route: String) {

    data object Home : Screen("home")

    data object Settings : Screen("settings")
}


@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val context = LocalContext.current

    val appContainer = AppContainer(context)


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


            val settingsViewModel: SettingsViewModel = viewModel(
                factory = SettingsViewModelFactory(
                    appContainer.settingsRepository
                )
            )


            SettingsScreen(
                onBackClick = {
                    navController.popBackStack()
                },

                viewModel = settingsViewModel
            )

        }
    }
}
