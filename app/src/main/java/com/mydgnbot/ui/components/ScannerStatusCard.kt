package com.mydgnbot.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mydgnbot.ui.theme.Black1
import com.mydgnbot.ui.theme.Emerald
import com.mydgnbot.ui.theme.TextMuted
import com.mydgnbot.ui.theme.TextPrimary
import com.mydgnbot.ui.theme.TextSecondary

@Composable
fun ScannerStatusCard(
    isRunning: Boolean,
    playerFound: Boolean,
    connected: Boolean
) {
    val title = when {
        !connected -> "Offline"
        playerFound -> "Player detected"
        isRunning -> "Scanning"
        else -> "Ready"
    }

    val subtitle = when {
        !connected -> "Reconnect to continue"
        playerFound -> "Target ready for action"
        isRunning -> "Searching in the background"
        else -> "Waiting for scan"
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Black1),
        border = BorderStroke(1.dp, Emerald.copy(alpha = 0.14f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = Emerald.copy(alpha = if (connected) 0.18f else 0.08f),
                        shape = RoundedCornerShape(999.dp)
                    )
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Text(
                    text = title.uppercase(),
                    color = TextPrimary,
                    style = androidx.compose.material3.MaterialTheme.typography.labelSmall
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = subtitle,
                    color = TextPrimary,
                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = if (connected) "Live monitoring active" else "Disconnected",
                    color = TextSecondary,
                    style = androidx.compose.material3.MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}