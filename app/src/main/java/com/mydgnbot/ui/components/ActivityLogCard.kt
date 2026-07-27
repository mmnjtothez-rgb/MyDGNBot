package com.mydgnbot.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mydgnbot.ui.theme.Black1
import com.mydgnbot.ui.theme.Emerald
import com.mydgnbot.ui.theme.Gold
import com.mydgnbot.ui.theme.TextMuted
import com.mydgnbot.ui.theme.TextPrimary
import com.mydgnbot.ui.theme.TextSecondary

@Composable
fun ActivityLogCard(
    logs: List<String>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Black1),
        border = BorderStroke(1.dp, Emerald.copy(alpha = 0.14f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Activity Log",
                color = TextPrimary,
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium
            )

            if (logs.isEmpty()) {
                Text(
                    text = "No activity yet",
                    color = TextSecondary,
                    style = androidx.compose.material3.MaterialTheme.typography.bodySmall
                )
            } else {
                logs.takeLast(5).forEachIndexed { index, log ->
                    ActivityRow(log = log)
                    if (index != logs.takeLast(5).lastIndex) {
                        Divider(
                            color = Emerald.copy(alpha = 0.08f),
                            thickness = 1.dp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ActivityRow(
    log: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Spacer(
            modifier = Modifier
                .padding(top = 6.dp)
                .background(Emerald.copy(alpha = 0.9f), RoundedCornerShape(999.dp))
                .padding(3.dp)
        )

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = log,
                color = TextPrimary,
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
            )
        }
    }
}