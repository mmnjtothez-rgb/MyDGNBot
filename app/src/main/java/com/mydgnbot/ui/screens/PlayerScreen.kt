package com.mydgnbot.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mydgnbot.ui.components.PlayerCard
import com.mydgnbot.ui.viewmodel.HomeViewModel

private val emerald = Color(0xFF42E8B4)
private val emeraldDim = Color(0xFF1DD9A2)
private val darkBg = Color(0xFF0B0F0C)
private val darkSurface = Color(0xFF060807)
private val gold = Color(0xFFF5C542)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(
    viewModel: HomeViewModel,
    onBackClick: () -> Unit
) {
    val player by viewModel.player.collectAsState()
    val isRunning by viewModel.isRunning.collectAsState()
    val botStatus by viewModel.botStatus.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Player",
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        when {
            player != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 14.dp)
                ) {
                    Spacer(modifier = Modifier.height(12.dp))

                    PlayerCard(
                        player = player,
                        onBought = {
                            viewModel.markBought()
                            onBackClick()
                        },
                        onCanceled = {
                            viewModel.cancelPlayer()
                            onBackClick()
                        }
                    )
                }
            }

            isRunning && botStatus.name == "SEARCHING" -> {
                LoadingPlayerState(
                    modifier = Modifier.padding(innerPadding)
                )
            }

            else -> {
                DarkPremiumEmptyState(
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

@Composable
private fun LoadingPlayerState(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF050806),
                        Color(0xFF0B0F0C)
                    )
                )
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = darkBg),
            border = BorderStroke(1.dp, Color(0xFF163122)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(color = emerald)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Searching for players...",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "The bot is actively checking MyDGN.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFB5B8B8),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun DarkPremiumEmptyState(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF050806),
                        Color(0xFF0B0F0C)
                    )
                )
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(26.dp),
            colors = CardDefaults.cardColors(containerColor = darkBg),
            border = BorderStroke(1.dp, Color(0xFF163122)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    shape = RoundedCornerShape(999.dp),
                    color = darkSurface,
                    border = BorderStroke(1.dp, emerald.copy(alpha = 0.45f))
                ) {
                    Box(
                        modifier = Modifier.padding(14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.HourglassEmpty,
                            contentDescription = null,
                            tint = emerald
                        )
                    }
                }

                Text(
                    text = "No player loaded yet",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "The bot is waiting for the next match to appear.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFB5B8B8),
                    textAlign = TextAlign.Center
                )

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF09100B),
                    border = BorderStroke(1.dp, gold.copy(alpha = 0.55f))
                ) {
                    Text(
                        text = "Keep the bot running to catch new players faster.",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.bodySmall,
                        color = gold,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}