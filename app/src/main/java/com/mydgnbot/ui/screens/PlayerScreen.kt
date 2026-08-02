package com.mydgnbot.ui.screens

import androidx.compose.runtime.Composable
import com.mydgnbot.domain.model.Player

@Composable
fun PlayerScreen(
    player: Player?,
    platform: String = "Console",
    playerType: String = "2",
    pollInterval: String = "10",
    timeRemainingText: String = "",
    onBoughtClick: () -> Unit = {},
    onCancelClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    if (player != null) {
        PlayerDetailBottomSheet(
            player = player,
            isVisible = true,
            onDismiss = onBackClick,
            onBoughtClick = onBoughtClick,
            onCancelClick = onCancelClick
        )
    }
}
