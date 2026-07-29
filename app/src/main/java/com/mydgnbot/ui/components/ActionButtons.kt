package com.mydgnbot.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.mydgnbot.R
import com.mydgnbot.ui.theme.Black1
import com.mydgnbot.ui.theme.Emerald
import com.mydgnbot.ui.theme.TextPrimary
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.graphics.CornerRadius
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border

enum class BotActionState {
    IDLE,
    SEARCHING,
    PLAYER_FOUND
}

@Composable
fun ActionButtons(
    state: BotActionState,
    onStartClick: () -> Unit,
    onStopClick: () -> Unit,
    onBoughtClick: () -> Unit,
    onCancelClick: () -> Unit,
    onHistoryClick: () -> Unit
) {
    val isRunning = state == BotActionState.SEARCHING

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Start/Stop bot button with sheen
        StartBotButton(
            isRunning = isRunning,
            onClick = {
                if (isRunning) onStopClick() else onStartClick()
            },
            modifier = Modifier.weight(2f)
        )

        // History button
        SmallSecondaryButton(
            label = "History",
            modifier = Modifier.weight(1f),
            onClick = onHistoryClick
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SmallSecondaryButton(
            label = "Bought",
            modifier = Modifier.weight(1f),
            onClick = onBoughtClick
        )

        SmallSecondaryButton(
            label = "Cancel",
            modifier = Modifier.weight(1f),
            onClick = onCancelClick
        )
    }
}

@Composable
private fun StartBotButton(
    isRunning: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val infiniteTransition = rememberInfiniteTransition(label = "startSheen")
    val sheenOffset by infiniteTransition.animateFloat(
        initialValue = -0.3f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "sheenOffset"
    )

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        animationSpec = tween(120),
        label = "pressScale"
    )

    Box(
        modifier = modifier
            .scale(scale)
            .clip(RoundedCornerShape(16.dp))
            .background(
                if (isRunning) Emerald else Black1,
                RoundedCornerShape(16.dp)
            )
            .drawBehind {
                // Inner highlight
                drawRoundRect(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color.White.copy(alpha = if (isRunning) 0.18f else 0.12f),
                            Color.Transparent
                        )
                    ),
                    cornerRadius = CornerRadius(16.dp.toPx())
                )

                // Sheen band (only when not running)
                if (!isRunning) {
                    val width = size.width
                    val height = size.height
                    val bandWidth = width * 0.35f
                    val x = (sheenOffset * width) - bandWidth
                    drawRect(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0f),
                                Color.White.copy(alpha = 0.35f),
                                Color.White.copy(alpha = 0f),
                            )
                        ),
                        topLeft = androidx.compose.ui.geometry.Offset(x, 0f),
                        size = androidx.compose.ui.geometry.Size(bandWidth, height)
                    )
                }
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(vertical = 10.dp, horizontal = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (isRunning) "Stop Bot" else "Start Bot",
            style = MaterialTheme.typography.labelLarge,
            color = if (isRunning) Color.Black else TextPrimary
        )
    }
}

@Composable
private fun SmallSecondaryButton(
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0xFF0A1110))
            .border(1.dp, Emerald.copy(alpha = 0.18f), RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp, horizontal = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = TextPrimary
        )
    }
}