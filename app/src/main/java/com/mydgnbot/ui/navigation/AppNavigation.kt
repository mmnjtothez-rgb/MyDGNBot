package com.mydgnbot.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.mydgnbot.data.AppContainer
import com.mydgnbot.domain.model.Player
import com.mydgnbot.ui.components.PlayerCard
import com.mydgnbot.ui.screens.HomeScreen
import com.mydgnbot.ui.screens.HistoryScreen
import com.mydgnbot.ui.screens.SettingsScreen
import com.mydgnbot.ui.viewmodel.HomeViewModel
import com.mydgnbot.ui.viewmodel.HomeViewModelFactory
import com.mydgnbot.ui.viewmodel.SettingsViewModel
import com.mydgnbot.ui.viewmodel.SettingsViewModelFactory
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Settings : Screen("settings")
    data object History : Screen("history")
    data object Player : Screen("player/{playerJson}") {
        fun createRoute(playerJson: String): String {
            val encoded = URLEncoder.encode(playerJson, StandardCharsets.UTF_8.toString())
            return "player/$encoded"
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val appContainer = AppContainer(context)
    val gson = Gson()

    val homeViewModel: HomeViewModel = viewModel(
        factory = HomeViewModelFactory(
            appContainer.playerRepository,
            appContainer.settingsRepository,
            appContainer.connectivityObserver,
            context.cacheDir
        )
    )

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onSettingsClick = {
                    navController.navigate(Screen.Settings.route)
                },
                onPlayerFound = { player ->
                    val json = gson.toJson(player)
                    navController.navigate(Screen.Player.createRoute(json))
                },
                onHistoryClick = {
                    navController.navigate(Screen.History.route)
                },
                viewModel = homeViewModel
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

        composable(Screen.History.route) {
            HistoryScreen(
                viewModel = homeViewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                onPlayerClick = { player ->
                    val json = gson.toJson(player)
                    navController.navigate(Screen.Player.createRoute(json))
                }
            )
        }

        composable(
            route = Screen.Player.route,
            arguments = listOf(navArgument("playerJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val encoded = backStackEntry.arguments?.getString("playerJson").orEmpty()
            val json = URLDecoder.decode(encoded, StandardCharsets.UTF_8.toString())
            val player = gson.fromJson(json, Player::class.java)
            PlayerCard(player = player)
        }
    }
}