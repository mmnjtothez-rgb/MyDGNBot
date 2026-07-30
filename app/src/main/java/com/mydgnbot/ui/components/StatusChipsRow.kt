package com.mydgnbot.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        StatusChip(
            text = if (connected) "Connected" else "Offline",
            connected = connected,
            modifier = Modifier.weight(1f),
            onClick = onSettingsClick
        )

        StatusChip(
            text = platform,
            connected = true,
            modifier = Modifier.weight(1f),
            onClick = {}
        )

        StatusChip(
            text = method,
            connected = true,
            modifier = Modifier.weight(1f),
            onClick = {}
        )

        StatusChip(
            text = interval,
            connected = true,
            modifier = Modifier.weight(1f),
            onClick = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StatusChip(
    text: String,
    connected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val container = if (connected) Emerald.copy(alpha = 0.16f) else TextSecondary.copy(alpha = 0.12f)
    val border = if (connected) Emerald.copy(alpha = 0.45f) else TextSecondary.copy(alpha = 0.25f)
    val content = if (connected) Emerald else TextSecondary

    AssistChip(
        onClick = onClick,
        label = {
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1
            )
        },
        leadingIcon = {
            Icon(
                imageVector = if (connected) Icons.Default.Check else Icons.Default.Close,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = content
            )
        },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = container,
            labelColor = content,
            leadingIconContentColor = content,
            disabledContainerColor = container,
            disabledLabelColor = content,
            disabledLeadingIconContentColor = content
        ),
        modifier = modifier.border(1.dp, border, RoundedCornerShape(20.dp))
    )
}