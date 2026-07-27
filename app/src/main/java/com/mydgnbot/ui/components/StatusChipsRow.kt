package com.mydgnbot.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mydgnbot.ui.theme.Emerald
import com.mydgnbot.ui.theme.TextPrimary
import com.mydgnbot.ui.theme.TextSecondary

@Composable
fun StatusChipsRow(
    connected: Boolean,
    platform: String,
    method: String,
    interval: String,
    onSettingsClick: () -> Unit,
    onPlatformClick: () -> Unit = {},
    onMethodClick: () -> Unit = {},
    onIntervalClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 2.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AssistChip(
            onClick = onPlatformClick,
            label = {
                Text(
                    text = platform.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = TextPrimary
                )
            },
            leadingIcon = {
                Text(
                    text = "●",
                    color = if (connected) Emerald else TextSecondary,
                    style = MaterialTheme.typography.labelSmall
                )
            },
            colors = AssistChipDefaults.assistChipColors(
                containerColor = Color(0xFF07110F),
                labelColor = TextPrimary,
                leadingIconContentColor = Emerald
            ),
            border = BorderStroke(
                1.dp,
                Emerald.copy(alpha = 0.22f)
            )
        )

        AssistChip(
            onClick = onMethodClick,
            label = {
                Text(
                    text = method.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = TextPrimary
                )
            },
            colors = AssistChipDefaults.assistChipColors(
                containerColor = Color(0xFF07110F),
                labelColor = TextPrimary
            ),
            border = BorderStroke(
                1.dp,
                Emerald.copy(alpha = 0.16f)
            )
        )

        AssistChip(
            onClick = onIntervalClick,
            label = {
                Text(
                    text = "${interval}s",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextPrimary
                )
            },
            colors = AssistChipDefaults.assistChipColors(
                containerColor = Color(0xFF07110F),
                labelColor = TextPrimary
            ),
            border = BorderStroke(
                1.dp,
                Emerald.copy(alpha = 0.16f)
            )
        )

        AssistChip(
            onClick = onSettingsClick,
            label = {
                Text(
                    text = if (connected) "ONLINE" else "OFFLINE",
                    style = MaterialTheme.typography.labelSmall,
                    color = if (connected) Emerald else TextSecondary
                )
            },
            colors = AssistChipDefaults.assistChipColors(
                containerColor = Color(0xFF07110F),
                labelColor = if (connected) Emerald else TextSecondary
            ),
            border = BorderStroke(
                1.dp,
                if (connected) Emerald.copy(alpha = 0.22f) else Color.White.copy(alpha = 0.08f)
            )
        )
    }
}