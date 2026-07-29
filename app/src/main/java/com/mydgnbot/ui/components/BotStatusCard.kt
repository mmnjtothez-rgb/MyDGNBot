package com.mydgnbot.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mydgnbot.ui.theme.Black1
import com.mydgnbot.ui.theme.Emerald
import com.mydgnbot.ui.theme.TextPrimary
import com.mydgnbot.ui.theme.TextSecondary

enum class BotStatus {
    SEARCHING,
    NO_PLAYER,
    PLAYER_FOUND,
    WAITING
}

@Composable
fun BotStatusCard(
    status: BotStatus,
    waitSeconds: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Black1),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = CardDefaults.outlinedCardBorder(
            enabled = true,
            borderColor = Emerald.copy(alpha = 0.14f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left: status text
            StatusColumn(
                status = status,
                waitSeconds = waitSeconds,
                modifier = Modifier.weight(1f)
            )

            // Right: FUT card carousel
            FutCardCarousel()
        }
    }
}

@Composable
private fun StatusColumn(
    status: BotStatus,
    waitSeconds: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        StatusLine(
            status = status,
            waitSeconds = waitSeconds
        )

        Text(
            text = subtitleFor(status),
            style = MaterialTheme.typography.labelSmall,
            color = TextSecondary
        )
    }
}

@Composable
private fun StatusLine(
    status: BotStatus,
    waitSeconds: Int
) {
    val text = when (status) {
        BotStatus.SEARCHING -> "Searching for player"
        BotStatus.NO_PLAYER -> "No player found"
        BotStatus.PLAYER_FOUND -> "Player found"
        BotStatus.WAITING -> "Waiting… ${waitSeconds}s"
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .width(8.dp)
                .height(8.dp)
                .clip(CircleShape)
                .background(
                    when (status) {
                        BotStatus.PLAYER_FOUND -> Emerald
                        BotStatus.SEARCHING -> Emerald.copy(alpha = 0.85f)
                        BotStatus.WAITING -> Emerald.copy(alpha = 0.6f)
                        BotStatus.NO_PLAYER -> Emerald.copy(alpha = 0.4f)
                    }
                )
        )

        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = TextPrimary
        )
    }
}

private fun subtitleFor(status: BotStatus): String {
    return when (status) {
        BotStatus.SEARCHING -> "Live market scan"
        BotStatus.NO_PLAYER -> "No matching listings yet"
        BotStatus.PLAYER_FOUND -> "Review player or buy"
        BotStatus.WAITING -> "Cooling down between scans"
    }
}

@Composable
private fun FutCardCarousel() {
    val cards = remember {
        listOf("Gold", "TOTW", "TOTS")
    }

    val transition = rememberInfiniteTransition(label = "futCards")
    val indexAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = cards.size.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "cardIndex"
    )
    val index = (indexAnim.toInt()) % cards.size

    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        cards.forEachIndexed { i, type ->
            val active = i == index
            FutMiniCard(type = type, active = active)
        }
    }
}

@Composable
private fun FutMiniCard(
    type: String,
    active: Boolean
) {
    val (baseColor, borderColor) = when (type) {
        "Gold" -> Color(0xFFC8A550) to Color(0xFFEED18A)
        "TOTW" -> Color(0xFF202020) to Emerald
        "TOTS" -> Color(0xFF1A2D5A) to Color(0xFF4FC3F7)
        else -> Black1 to Emerald
    }

    Box(
        modifier = Modifier
            .width(32.dp)
            .height(48.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
                baseColor.copy(alpha = if (active) 1f else 0.75f)
            )
            .background(
                Color.Black.copy(alpha = 0.4f),
                RoundedCornerShape(10.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        // Simple border effect via overlay
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Transparent)
        )

        if (active) {
            Text(
                text = type,
                style = MaterialTheme.typography.labelSmall,
                color = Color.White
            )
        }
    }
}
