package com.mydgnbot.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import com.mydgnbot.ui.theme.Black1
import com.mydgnbot.ui.theme.Emerald
import com.mydgnbot.ui.theme.TextSecondary

@Composable
fun RadarScannerCard(
    isRunning: Boolean,
    playerFound: Boolean,
    connected: Boolean,
    modifier: Modifier = Modifier
) {
    val transition = rememberInfiniteTransition(label = "radar")
    val angle by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 10_000, // slower sweep: 10s per rotation
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "radarAngle"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(2f)
            .background(Black1, RoundedCornerShape(18.dp))
            .padding(14.dp)
    ) {
        Canvas(
            modifier = Modifier
                .matchParentSize()
        ) {
            val radius = size.minDimension / 2.4f
            val center = center

            // Static rings
            drawCircle(
                color = Emerald.copy(alpha = 0.16f),
                radius = radius * 0.4f,
                center = center
            )
            drawCircle(
                color = Emerald.copy(alpha = 0.12f),
                radius = radius * 0.7f,
                center = center
            )
            drawCircle(
                color = Emerald.copy(alpha = 0.08f),
                radius = radius,
                center = center
            )

            // Sweep beam (only when running)
            if (isRunning && connected) {
                rotate(degrees = angle, pivot = center) {
                    val sweepAngle = 45f
                    drawArc(
                        color = Emerald.copy(alpha = 0.25f),
                        startAngle = -sweepAngle / 2,
                        sweepAngle = sweepAngle,
                        useCenter = true,
                        topLeft = Offset(
                            center.x - radius,
                            center.y - radius
                        ),
                        size = Size(radius * 2, radius * 2)
                    )
                }
            }

            // Calmer core
            drawCircle(
                color = Emerald.copy(alpha = if (playerFound) 0.6f else 0.35f),
                radius = radius * 0.12f,
                center = center
            )

            // Small subtle inner dot
            drawCircle(
                color = Color.Black.copy(alpha = 0.4f),
                radius = radius * 0.04f,
                center = center
            )
        }

        // Status text anchored at bottom
        val statusText = when {
            !connected -> "Offline • Check connection"
            playerFound -> "Player found"
            isRunning -> "Searching for player"
            else -> "Idle"
        }

        Text(
            text = statusText,
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondary,
            modifier = Modifier
                .align(Alignment.BottomStart)
        )
    }
}