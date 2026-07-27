package com.mydgnbot.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mydgnbot.ui.components.ActionButtons
import com.mydgnbot.ui.components.ActivityLogCard
import com.mydgnbot.ui.components.BotActionState
import com.mydgnbot.ui.components.ScannerStatusCard
import com.mydgnbot.ui.components.StatusStrip
import com.mydgnbot.ui.theme.Black0
import com.mydgnbot.ui.theme.TextPrimary
import com.mydgnbot.ui.theme.TextSecondary
import com.mydgnbot.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    onSettingsClick: () -> Unit,
    viewModel: HomeViewModel
) {
    val player by viewModel.player.collectAsState()
    val settings by viewModel.settings.collectAsState()
    val isOnline by viewModel.isOnline.collectAsState()
    val isRunning by viewModel.isRunning.collectAsState()
    val logs by viewModel.logs.collectAsState()

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
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
                    .padding(top = 12.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "MyDGNBot",
                        style = MaterialTheme.typography.displayMedium,
                        color = TextPrimary
                    )
                    Text(
                        text = "Midnight green • AMOLED black • premium bot control",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }

                StatusStrip(
                    platform = platform,
                    method = method,
                    interval = interval,
                    connected = isOnline,
                    onSettingsClick = onSettingsClick
                )

                ScannerStatusCard(
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
                    onSettingsClick = onSettingsClick
                )

                ActivityLogCard(logs = logs)
            }
        }
    }
}