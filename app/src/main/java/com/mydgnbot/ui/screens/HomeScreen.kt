package com.mydgnbot.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mydgnbot.domain.model.LogEntry
import com.mydgnbot.ui.components.ActionButtons
import com.mydgnbot.ui.components.BotActionState
import com.mydgnbot.ui.components.ScannerStatusCard
import com.mydgnbot.ui.components.StatusChipsRow
import com.mydgnbot.ui.viewmodel.HomeViewModel

private val emerald = Color(0xFF42E8B4)
private val darkBg = Color(0xFF0B0F0C)
private val darkSurface = Color(0xFF060807)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onSettingsClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onPlayerFound: () -> Unit
) {
    val isRunning by viewModel.isRunning.collectAsState()
    val isOnline by viewModel.isOnline.collectAsState()
    val logs by viewModel.logs.collectAsState()
    val activePlayer by viewModel.player.collectAsState()
    val settings by viewModel.settings.collectAsState()

    // Read settings directly from HomeViewModel settings state
    val platform = settings["platform"] ?: "Console"
    val method = settings["method"] ?: "API"
    val pollSeconds = settings["poll_interval"] ?: "10"
    val interval = "${pollSeconds}s"

    // Derive BotActionState for ActionButtons
    val actionState = when {
        activePlayer != null -> BotActionState.PLAYER_FOUND
        isRunning -> BotActionState.SEARCHING
        else -> BotActionState.IDLE
    }

    // Trigger navigation immediately when a player is caught
    LaunchedEffect(activePlayer) {
        if (activePlayer != null) {
            onPlayerFound()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "MYDGN",
                            fontWeight = FontWeight.Black,
                            fontSize = 20.sp,
                            color = emerald
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "BOT",
                            fontWeight = FontWeight.Light,
                            fontSize = 20.sp,
                            color = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onHistoryClick) {
                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = "History",
                            tint = Color(0xFFE5E7EB)
                        )
                    }
                    IconButton(onClick = onSettingsClick) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = Color(0xFFE5E7EB)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = darkSurface
                )
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = darkSurface,
                border = BorderStroke(1.dp, Color(0xFF163122))
            ) {
                Box(modifier = Modifier.padding(16.dp)) {
                    ActionButtons(
                        state = actionState,
                        onStartClick = { viewModel.startBot() },
                        onStopClick = { viewModel.stopBot() },
                        onBoughtClick = { viewModel.markBought() },
                        onCancelClick = { viewModel.cancelPlayer() },
                        onHistoryClick = onHistoryClick
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(darkSurface, darkBg)
                    )
                )
                .padding(horizontal = 14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            // Radar Scanner Component
            ScannerStatusCard(
                isRunning = isRunning,
                playerFound = activePlayer != null,
                connected = isOnline
            )

            // Status Chips Row Component
            StatusChipsRow(
                connected = isOnline,
                platform = platform,
                method = method,
                interval = interval,
                onSettingsClick = onSettingsClick
            )

            // Live Activity Log Feed
            Text(
                text = "Live Activity Log",
                style = MaterialTheme.typography.titleSmall,
                color = Color(0xFFB5B8B8),
                fontWeight = FontWeight.SemiBold
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = darkBg),
                border = BorderStroke(1.dp, Color(0xFF163122))
            ) {
                if (logs.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No bot activity yet. Tap 'Start Bot' to listen.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF6B7280)
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(logs.reversed()) { log ->
                            AnimatedVisibility(
                                visible = true,
                                enter = fadeIn() + slideInVertically()
                            ) {
                                LogItemRow(log = log)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LogItemRow(log: LogEntry) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        color = Color(0xFF060907),
        border = BorderStroke(1.dp, Color(0xFF122218))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = log.message,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFFE5E7EB),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = log.timestamp,
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF6B7280)
            )
        }
    }
}
