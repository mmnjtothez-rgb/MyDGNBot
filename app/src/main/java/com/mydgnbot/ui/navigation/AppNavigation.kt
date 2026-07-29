package com.mydgnbot.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mydgnbot.data.datastore.SettingsDataStore
import com.mydgnbot.data.network.ConnectivityObserver
import com.mydgnbot.data.network.NetworkConnectivityObserver
import com.mydgnbot.data.repository.PlayerRepository
import com.mydgnbot.data.repository.SettingsRepository
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

    // Repositories
    val settingsRepository = remember {
        SettingsRepository(SettingsDataStore(context))
    }
    val playerRepository = remember { PlayerRepository() }

    // Real connectivity observer
    val connectivityObserver: ConnectivityObserver = remember {
        NetworkConnectivityObserver(context)
    }

    // ViewModels
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

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                viewModel = homeViewModel,
                onSettingsClick = { navController.navigate("settings") },
                onHistoryClick = { navController.navigate("history") },
                onPlayerFound = {
                    navController.navigate("player")
                }
            )
        }

        composable("settings") {
            SettingsScreen(
                onBackClick = { navController.popBackStack() },
                viewModel = settingsViewModel
            )
        }

        composable("player") {
            PlayerScreen(
                viewModel = homeViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }

        // Add history route only if you have a HistoryScreen:
        // composable("history") {
        //     HistoryScreen(
        //         viewModel = homeViewModel,
        //         onBackClick = { navController.popBackStack() }
        //     )
        // }
    }
}