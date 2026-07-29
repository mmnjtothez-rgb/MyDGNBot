package com.mydgnbot.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Chip
import androidx.compose.material3.ChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mydgnbot.ui.theme.Emerald
import com.mydgnbot.ui.theme.TextSecondary

@Composable
fun StatusChipsRow(
    connected: Boolean,
    platform: String,
    method: String,
    interval: String,
    onSettingsClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Platform chip
        Chip(
            label = {
                Text(
                    text = platform,
                    style = MaterialTheme.typography.labelSmall
                )
            },
            onClick = { },
            colors = ChipDefaults.chipColors(
                containerColor = Emerald.copy(alpha = 0.18f)
            ),
            modifier = Modifier.weight(1f)
        )

        // Method chip (Safe / Quicksell)
        Chip(
            label = {
                Text(
                    text = method,
                    style = MaterialTheme.typography.labelSmall
                )
            },
            onClick = { },
            colors = ChipDefaults.chipColors(
                containerColor = Emerald.copy(alpha = 0.18f)
            ),
            modifier = Modifier.weight(1f)
        )

        // Interval chip
        Chip(
            label = {
                Text(
                    text = interval,
                    style = MaterialTheme.typography.labelSmall
                )
            },
            onClick = { },
            colors = ChipDefaults.chipColors(
                containerColor = Emerald.copy(alpha = 0.18f)
            ),
            modifier = Modifier.weight(1f)
        )

        // Connection status chip
        Chip(
            label = {
                Text(
                    text = if (connected) "Connected" else "Offline",
                    style = MaterialTheme.typography.labelSmall
                )
            },
            onClick = onSettingsClick,
            colors = ChipDefaults.chipColors(
                containerColor = if (connected)
                    Emerald.copy(alpha = 0.25f)
                else
                    TextSecondary.copy(alpha = 0.25f)
            ),
            leadingIcon = {
                Icon(
                    imageVector = if (connected)
                        Icons.Default.Check
                    else
                        Icons.Default.Close,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = if (connected) Emerald else TextSecondary
                )
            },
            modifier = Modifier
                .weight(1f)
                .clickable(onClick = onSettingsClick)
        )
    }
}