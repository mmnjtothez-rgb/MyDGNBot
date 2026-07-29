package com.mydgnbot.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mydgnbot.ui.components.ActionButtons
import com.mydgnbot.ui.components.BotActionState
import com.mydgnbot.ui.components.BotStatusCard
import com.mydgnbot.ui.components.RadarScannerCard
import com.mydgnbot.ui.components.StatusChipsRow
import com.mydgnbot.ui.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onSettingsClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onPlayerFound: () -> Unit
) {
    val player by viewModel.player.collectAsState()
    val isRunning by viewModel.isRunning.collectAsState()
    val isOnline by viewModel.isOnline.collectAsState()
    val settings by viewModel.settings.collectAsState()
    val botStatus by viewModel.botStatus.collectAsState()
    val waitSeconds by viewModel.waitSeconds.collectAsState()

    LaunchedEffect(player) {
        if (player != null) onPlayerFound()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "MyDGN Bot",
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                actions = {
                    IconButton(onClick = onSettingsClick) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Settings"
                        )
                    }
                }
            )
        }
    ) { padding ->
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 12.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Top
        ) {
            StatusChipsRow(
                connected = isOnline,
                platform = settings["platform"] ?: "platform",
                method = settings["method"] ?: "method",
                interval = settings["interval"] ?: "0",
                onSettingsClick = onSettingsClick
            )

            Spacer(modifier = Modifier.height(10.dp))

            RadarScannerCard(
                isRunning = isRunning,
                playerFound = player != null,
                connected = isOnline,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            BotStatusCard(
                status = botStatus,
                waitSeconds = waitSeconds,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            val actionState = when {
                isRunning -> BotActionState.SEARCHING
                player != null -> BotActionState.PLAYER_FOUND
                else -> BotActionState.IDLE
            }

            ActionButtons(
                state = actionState,
                onStartClick = { viewModel.startBot() },
                onStopClick = { viewModel.stopBot() },
                onBoughtClick = { viewModel.markBought() },
                onCancelClick = { viewModel.cancelPlayer() },
                onHistoryClick = {
                    viewModel.requestHistory()
                    onHistoryClick()
                }
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}