package com.mydgnbot.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mydgnbot.ui.theme.Emerald
import com.mydgnbot.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatusChipsRow(
    connected: Boolean,
    platform: String,
    method: String,
    interval: String,
    onSettingsClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Platform chip
        AssistChip(
            onClick = { },
            label = {
                Text(
                    text = platform,
                    style = MaterialTheme.typography.labelSmall
                )
            },
            colors = AssistChipDefaults.assistChipColors(
                containerColor = Emerald.copy(alpha = 0.18f)
            ),
            modifier = Modifier.weight(1f)
        )

        // Method chip (Safe / Quicksell)
        AssistChip(
            onClick = { },
            label = {
                Text(
                    text = method,
                    style = MaterialTheme.typography.labelSmall
                )
            },
            colors = AssistChipDefaults.assistChipColors(
                containerColor = Emerald.copy(alpha = 0.18f)
            ),
            modifier = Modifier.weight(1f)
        )

        // Interval chip
        AssistChip(
            onClick = { },
            label = {
                Text(
                    text = interval,
                    style = MaterialTheme.typography.labelSmall
                )
            },
            colors = AssistChipDefaults.assistChipColors(
                containerColor = Emerald.copy(alpha = 0.18f)
            ),
            modifier = Modifier.weight(1f)
        )

        // Connection status chip
        AssistChip(
            onClick = onSettingsClick,
            label = {
                Text(
                    text = if (connected) "Connected" else "Offline",
                    style = MaterialTheme.typography.labelSmall
                )
            },
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
            colors = AssistChipDefaults.assistChipColors(
                containerColor = if (connected)
                    Emerald.copy(alpha = 0.25f)
                else
                    TextSecondary.copy(alpha = 0.25f)
            ),
            modifier = Modifier
                .weight(1f)
                .clickable(onClick = onSettingsClick)
        )
    }
}