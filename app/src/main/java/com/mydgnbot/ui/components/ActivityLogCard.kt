package com.mydgnbot.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.dp
import com.mydgnbot.domain.model.LogEntry
import com.mydgnbot.ui.theme.Black1
import com.mydgnbot.ui.theme.Emerald
import com.mydgnbot.ui.theme.TextMuted
import com.mydgnbot.ui.theme.TextPrimary
import com.mydgnbot.ui.theme.TextSecondary

@Composable
fun ActivityLogCard(
    logs: List<LogEntry>
) {
    val series = remember {
        listOf(12f, 14f, 10f, 18f, 16f, 22f, 19f, 26f, 21f, 29f, 24f, 31f)
    }

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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Activity Log",
                        color = TextPrimary,
                        style = androidx.compose.material3.MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Live market activity",
                        color = TextSecondary,
                        style = androidx.compose.material3.MaterialTheme.typography.bodySmall
                    )
                }

                Text(
                    text = "Last bought: 2s ago",
                    color = Emerald,
                    style = androidx.compose.material3.MaterialTheme.typography.labelMedium
                )
            }

            SparklineCard(values = series)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatPill(value = "12", label = "Found", modifier = Modifier.weight(1f))
                StatPill(value = "8", label = "Bought", modifier = Modifier.weight(1f))
                StatPill(value = "4", label = "Live", modifier = Modifier.weight(1f))
            }

            Divider(color = Emerald.copy(alpha = 0.08f), thickness = 1.dp)

            if (logs.isNotEmpty()) {
                logs.takeLast(4).forEachIndexed { index, log ->
                    ActivityRow(log = log)
                    if (index != logs.takeLast(4).lastIndex) {
                        Spacer(modifier = Modifier.size(6.dp))
                    }
                }
            } else {
                Text(
                    text = "Market feed is warming up...",
                    color = TextSecondary,
                    style = androidx.compose.material3.MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
private fun SparklineCard(
    values: List<Float>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(92.dp)
            .background(Color(0xFF07110F), RoundedCornerShape(16.dp))
            .padding(10.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxWidth().height(72.dp)) {
            val min = values.minOrNull() ?: 0f
            val max = values.maxOrNull() ?: 1f
            val range = (max - min).takeIf { it != 0f } ?: 1f
            val step = size.width / (values.size - 1).coerceAtLeast(1)

            val points = values.mapIndexed { i, v ->
                val x = step * i
                val y = size.height - ((v - min) / range) * size.height
                Offset(x, y)
            }

            val path = Path().apply {
                if (points.isNotEmpty()) {
                    moveTo(points.first().x, points.first().y)
                    for (i in 1 until points.size) {
                        val prev = points[i - 1]
                        val curr = points[i]
                        val midX = (prev.x + curr.x) / 2f
                        cubicTo(midX, prev.y, midX, curr.y, curr.x, curr.y)
                    }
                }
            }

            drawPath(
                path = path,
                color = Emerald,
                style = androidx.compose.ui.graphics.drawscope.Stroke(
                    width = 4f,
                    pathEffect = PathEffect.cornerPathEffect(8f)
                )
            )

            drawPath(
                path = path,
                color = Emerald.copy(alpha = 0.10f)
            )

            points.forEach { p ->
                drawCircle(Emerald, radius = 4f, center = p)
                drawCircle(Color.White, radius = 1.5f, center = p)
            }
        }
    }
}

@Composable
private fun StatPill(
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(Color(0xFF07110F), RoundedCornerShape(14.dp))
            .padding(vertical = 12.dp, horizontal = 10.dp)
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            Text(
                text = value,
                color = TextPrimary,
                style = androidx.compose.material3.MaterialTheme.typography.titleSmall
            )
            Text(
                text = label.uppercase(),
                color = TextSecondary,
                style = androidx.compose.material3.MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Composable
private fun ActivityRow(
    log: LogEntry
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Spacer(
            modifier = Modifier
                .padding(top = 6.dp)
                .size(8.dp)
                .background(Emerald, RoundedCornerShape(999.dp))
        )

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = log.message,
                color = TextPrimary,
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
            )

            if (log.timestamp.isNotBlank()) {
                Text(
                    text = log.timestamp,
                    color = TextMuted,
                    style = androidx.compose.material3.MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}