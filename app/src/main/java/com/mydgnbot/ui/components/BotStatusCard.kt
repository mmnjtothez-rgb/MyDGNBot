package com.mydgnbot.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mydgnbot.R
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
            .fillMaxWidth()
            .border(1.dp, Emerald.copy(alpha = 0.14f), RoundedCornerShape(18.dp)),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Black1),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                StatusLine(status = status, waitSeconds = waitSeconds)
                Text(
                    text = subtitleFor(status),
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondary
                )
            }

            RotatingFc26Cards()
        }
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
private fun RotatingFc26Cards() {
    val cards = remember {
        listOf(
            R.drawable.fc26_gold_card,
            R.drawable.fc26_totw_card,
            R.drawable.fc26_tots_card,
            R.drawable.fc26_toty_card,
            R.drawable.fc26_hero_card,
            R.drawable.fc26_icon_card,
            R.drawable.fc26_ucl_card,
            R.drawable.fc26_thunderstruck_card,
            R.drawable.fc26_captains_card,
            R.drawable.fc26_futbirthday_card,
            R.drawable.fc26_trophyicon_card,
            R.drawable.fc26_scream_card,
            R.drawable.fc26_winter_card,
            R.drawable.fc26_ratingreload_card
        )
    }

    val transition = rememberInfiniteTransition()
    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = cards.size.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 7000, easing = LinearEasing)
        )
    )

    val activeIndex = (progress.toInt()) % cards.size

    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        cards.forEachIndexed { index, resId ->
            val active = index == activeIndex
            Fc26CardThumb(
                resId = resId,
                active = active
            )
        }
    }
}

@Composable
private fun Fc26CardThumb(
    resId: Int,
    active: Boolean
) {
    val scale by androidx.compose.animation.core.animateFloatAsState(
        targetValue = if (active) 1.15f else 0.92f,
        animationSpec = tween(450)
    )

    val rotation by androidx.compose.animation.core.animateFloatAsState(
        targetValue = if (active) 0f else 18f,
        animationSpec = tween(450)
    )

    Box(
        modifier = Modifier
            .width(36.dp)
            .height(72.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                rotationY = rotation
                cameraDistance = 18 * density
            }
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.foundation.Image(
            painter = painterResource(id = resId),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )
    }
}