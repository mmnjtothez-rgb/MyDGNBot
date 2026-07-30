package com.mydgnbot.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.mydgnbot.domain.model.Player
import com.mydgnbot.ui.components.PlayerCard
import com.mydgnbot.ui.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(
    viewModel: HomeViewModel,
    onBackClick: () -> Unit,
    player: Player
) {
    val livePlayer by viewModel.player.collectAsState()
    val isPaused by viewModel.isPaused.collectAsState()

    // Pause bot when entering player screen
    LaunchedEffect(Unit) {
        if (!isPaused) {
            viewModel.setPaused(true)
        }
    }

    // When live player becomes null (after buy/cancel), resume and go back
    LaunchedEffect(livePlayer) {
        if (livePlayer == null) {
            viewModel.setPaused(false)
            onBackClick()
        }
    }

    // Also resume on back click (e.g., user presses back without buy/cancel)
    val wrappedOnBackClick = {
        viewModel.setPaused(false)
        onBackClick()
    }

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
                    IconButton(onClick = wrappedOnBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
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
                    // Bot will resume when livePlayer becomes null
                },
                onCanceled = {
                    viewModel.cancelPlayer()
                    // Bot will resume when livePlayer becomes null
                }
            )
        }
    }
}