package com.mydgnbot.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mydgnbot.ui.theme.Black1
import com.mydgnbot.ui.theme.Emerald
import com.mydgnbot.ui.theme.TextPrimary
import com.mydgnbot.ui.theme.TextSecondary
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun RadarScannerCard(
    isRunning: Boolean,
    playerFound: Boolean,
    connected: Boolean
) {
    val transition = rememberInfiniteTransition(label = "radar")
    val sweep by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "sweep"
    )
    val pulse by transition.animateFloat(
        initialValue = 0.55f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1100),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Black1),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(Color(0xFF07110F))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxWidth().height(268.dp)) {
                val center = Offset(size.width / 2f, size.height / 2f + 8f)
                val maxR = size.minDimension * 0.36f

                listOf(1f, 0.72f, 0.45f).forEachIndexed { index, mult ->
                    drawCircle(
                        color = Emerald.copy(alpha = (0.06f + index * 0.04f) * pulse),
                        radius = maxR * mult,
                        center = center,
                        style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2f)
                    )
                }

                for (i in 0 until 4) {
                    val angle = Math.toRadians((sweep + i * 90f).toDouble())
                    val x = center.x + cos(angle).toFloat() * maxR
                    val y = center.y + sin(angle).toFloat() * maxR
                    drawLine(
                        color = Emerald.copy(alpha = 0.12f),
                        start = center,
                        end = Offset(x, y),
                        strokeWidth = 2f
                    )
                }

                val beamAngle = Math.toRadians(sweep.toDouble())
                val beamX = center.x + cos(beamAngle).toFloat() * maxR
                val beamY = center.y + sin(beamAngle).toFloat() * maxR
                drawLine(
                    color = Emerald.copy(alpha = 0.9f),
                    start = center,
                    end = Offset(beamX, beamY),
                    strokeWidth = 7f
                )

                drawCircle(
                    color = Emerald.copy(alpha = 0.18f),
                    radius = maxR + 18f,
                    center = center,
                    style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.5f)
                )

                drawCircle(
                    color = Emerald.copy(alpha = 0.9f),
                    radius = 12f,
                    center = center
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = if (playerFound) "PLAYER FOUND" else if (isRunning) "SCANNING" else "READY",
                    color = Emerald,
                    style = androidx.compose.material3.MaterialTheme.typography.titleMedium
                )
                Text(
                    text = if (connected) "Live radar active" else "Offline monitoring",
                    color = TextSecondary,
                    style = androidx.compose.material3.MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}