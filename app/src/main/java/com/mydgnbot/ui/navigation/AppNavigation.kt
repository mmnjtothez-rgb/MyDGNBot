package com.mydgnbot.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mydgnbot.data.datastore.SettingsDataStore
import com.mydgnbot.data.network.ConnectivityObserver
import com.mydgnbot.data.network.NetworkConnectivityObserver
import com.mydgnbot.data.repository.PlayerRepository
import com.mydgnbot.data.repository.SettingsRepository
import com.mydgnbot.ui.screens.HistoryScreen
import com.mydgnbot.ui.screens.HomeScreen
import com.mydgnbot.ui.screens.PlayerScreen
import com.mydgnbot.ui.screens.SettingsScreen
import com.mydgnbot.ui.viewmodel.HomeViewModel
import com.mydgnbot.ui.viewmodel.HomeViewModelFactory
import com.mydgnbot.ui.viewmodel.SettingsViewModel
import com.mydgnbot.ui.viewmodel.SettingsViewModelFactory

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = androidx.compose.ui.platform.LocalContext.current

    val settingsRepository = remember {
        SettingsRepository(SettingsDataStore(context))
    }
    val playerRepository = remember { PlayerRepository() }

    val connectivityObserver: ConnectivityObserver = remember {
        NetworkConnectivityObserver(context)
    }

    val settingsViewModel: SettingsViewModel = viewModel(
        factory = SettingsViewModelFactory(settingsRepository)
    )

    val homeViewModel: HomeViewModel = viewModel(
        factory = HomeViewModelFactory(
            playerRepository = playerRepository,
            settingsRepository = settingsRepository,
            connectivityObserver = connectivityObserver,
            cacheDir = context.cacheDir
        )
    )

    fun navigateBackToHome() {
        if (!navController.popBackStack()) {
            navController.navigate("home") {
                popUpTo("home") { inclusive = true }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                viewModel = homeViewModel,
                onSettingsClick = { navController.navigate("settings") },
                onHistoryClick = { navController.navigate("history") },
                onPlayerClick = {
                    if (homeViewModel.player.value != null) {
                        navController.navigate("player")
                    }
                }
            )
        }

        composable("settings") {
            SettingsScreen(
                onBackClick = { navigateBackToHome() },
                viewModel = settingsViewModel
            )
        }

        composable("player") {
            val playerState by homeViewModel.player.collectAsState()
            val settingsState by homeViewModel.settings.collectAsState()
            val player = playerState

            if (player != null) {
                PlayerScreen(
                    player = player,
                    platform = settingsState["platform"] ?: "Console",
                    playerType = settingsState["player_type"] ?: "2",
                    pollInterval = settingsState["poll_interval"] ?: "10",
                    timeRemainingText = player.marketExpiry.toString().ifEmpty { "59:22" },
                    onBoughtClick = {
                        homeViewModel.markBought()
                        navigateBackToHome()
                    },
                    onCancelClick = {
                        homeViewModel.cancelPlayer()
                        navigateBackToHome()
                    }
                )
            }
        }

        composable(
            route = "player/history/{playerId}",
            arguments = listOf(
                navArgument("playerId") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { backStackEntry ->
            val playerId = backStackEntry.arguments?.getString("playerId")
            val settingsState by homeViewModel.settings.collectAsState()
            val player = if (playerId != null) {
                homeViewModel.recentPlayers.value.find { p ->
                    p.resourceId.ifBlank { p.transactionId } == playerId
                }
            } else {
                null
            }

            if (player != null) {
                PlayerScreen(
                    player = player,
                    platform = settingsState["platform"] ?: "Console",
                    playerType = settingsState["player_type"] ?: "2",
                    pollInterval = settingsState["poll_interval"] ?: "10",
                    timeRemainingText = player.marketExpiry.toString().ifEmpty { "59:22" },
                    onBoughtClick = {
                        homeViewModel.markBought()
                        navigateBackToHome()
                    },
                    onCancelClick = {
                        homeViewModel.cancelPlayer()
                        navigateBackToHome()
                    }
                )
            }
        }

        composable("history") {
            HistoryScreen(
                viewModel = homeViewModel,
                onBackClick = { navigateBackToHome() },
                onPlayerClick = { player ->
                    val id = player.resourceId.ifBlank { player.transactionId }
                    if (id.isNotBlank()) {
                        navController.navigate("player/history/$id")
                    }
                }
            )
        }
    }
}
