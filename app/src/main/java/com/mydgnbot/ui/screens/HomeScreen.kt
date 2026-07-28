package com.mydgnbot.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.mydgnbot.domain.model.Player
import com.mydgnbot.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    onSettingsClick: () -> Unit,
    onPlayerFound: (Player) -> Unit,
    onHistoryClick: () -> Unit,
    viewModel: HomeViewModel
) {
    val player by viewModel.player.collectAsState()
    val logs by viewModel.logs.collectAsState()
    val showHistory by viewModel.showHistory.collectAsState()

    // keep your existing UI here,
    // but use player/logs/showHistory from above
}