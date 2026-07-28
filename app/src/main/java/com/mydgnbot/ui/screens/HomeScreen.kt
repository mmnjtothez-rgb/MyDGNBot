package com.mydgnbot.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mydgnbot.domain.model.Player
import com.mydgnbot.ui.components.ActionButtons
import com.mydgnbot.ui.components.ActivityLogCard
import com.mydgnbot.ui.components.BotActionState
import com.mydgnbot.ui.components.RadarScannerCard
import com.mydgnbot.ui.components.StatusChipsRow
import com.mydgnbot.ui.theme.Black0
import com.mydgnbot.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    onSettingsClick: () -> Unit,
    onPlayerFound: (Player) -> Unit,
    onHistoryClick: () -> Unit,
    viewModel: HomeViewModel
) {
    val player by viewModel.player.collectAsState()
    val settings by viewModel.settings.collectAsState()
    val isOnline by viewModel.isOnline.collectAsState()
    val isRunning by viewModel.isRunning.collectAsState()
    val logs by viewModel.logs.collectAsState()

    LaunchedEffect(player) {
        player?.let(onPlayerFound)
    }

    val platform = settings["platform"] ?: "Console"
    val method = if (settings["player_type"] == "1") "Safe" else "Quick Sell"
    val interval = settings["poll_interval"] ?: "10"

    val actionState = when {
        player != null -> BotActionState.PLAYER_FOUND
        isRunning -> BotActionState.SEARCHING
        else -> BotActionState.IDLE
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Black0
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Black0)
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TopHeader(
                    onSettingsClick = onSettingsClick
                )

                StatusChipsRow(
                    connected = isOnline,
                    platform = platform,
                    method = method,
                    interval = interval,
                    onSettingsClick = onSettingsClick,
                    onPlatformClick = {},
                    onMethodClick = {},
                    onIntervalClick = {}
                )

                RadarScannerCard(
                    isRunning = isRunning,
                    playerFound = player != null,
                    connected = isOnline
                )

                ActionButtons(
                    state = actionState,
                    onStartClick = viewModel::startBot,
                    onStopClick = viewModel::stopBot,
                    onBoughtClick = viewModel::markBought,
                    onCancelClick = viewModel::cancelPlayer,
                    onHistoryClick = onHistoryClick
                )

                ActivityLogCard(logs = logs)
            }
        }
    }
}

@Composable
private fun TopHeader(
    onSettingsClick: () -> Unit
) {
    androidx.compose.foundation.layout.Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 2.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        androidx.compose.material3.Text(
            text = "DGBot",
            style = MaterialTheme.typography.titleLarge,
            color = com.mydgnbot.ui.theme.TextPrimary
        )

        androidx.compose.foundation.layout.Spacer(modifier = Modifier.weight(1f))

        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .size(28.dp)
                .background(
                    color = androidx.compose.ui.graphics.Color(0xFF0A1110),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp)
                )
                .clickable(onClick = onSettingsClick),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            androidx.compose.material3.Icon(
                painter = androidx.compose.ui.res.painterResource(com.mydgnbot.R.drawable.ic_settings),
                contentDescription = "Settings",
                tint = com.mydgnbot.ui.theme.Emerald,
                modifier = Modifier.size(15.dp)
            )
        }
    }
}