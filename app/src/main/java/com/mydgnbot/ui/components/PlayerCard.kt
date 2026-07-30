package com.mydgnbot.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mydgnbot.R
import com.mydgnbot.domain.model.Player
import kotlinx.coroutines.delay

private val emerald = Color(0xFF42E8B4)
private val emeraldDim = Color(0xFF1DD9A2)
private val darkBg = Color(0xFF0B0F0C)
private val darkSurface = Color(0xFF060807)
private val gold = Color(0xFFF5C542)
private val amber = Color(0xFFFFC857)
private val red = Color(0xFFFF5C5C)

@Composable
fun PlayerCard(
    player: Player?,
    onBought: () -> Unit,
    onCanceled: () -> Unit
) {
    if (player == null) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = darkBg),
            border = BorderStroke(1.dp, Color(0xFF163122))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Waiting for player...",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFFE5E7EB)
                )
            }
        }
        return
    }

    val lockExpiresMillis = (player.lockExpires ?: 0L) * 1000L
    var remainingLockMillis by remember(player.lockExpires) {
        mutableStateOf(lockExpiresMillis - System.currentTimeMillis())
    }

    LaunchedEffect(player.lockExpires) {
        while (remainingLockMillis > 0L) {
            delay(1000)
            remainingLockMillis = lockExpiresMillis - System.currentTimeMillis()
        }
        if (remainingLockMillis <= 0L) {
            onCanceled()
        }
    }

    val lockSeconds = (remainingLockMillis / 1000L).coerceAtLeast(0L).toInt()

    val lockTimerColor by animateColorAsState(
        targetValue = when {
            lockSeconds > 120 -> emerald
            lockSeconds > 30 -> amber
            else -> red
        },
        label = "lockTimerColor"
    )

    val pulseTransition = rememberInfiniteTransition(label = "pulse")
    val pulseAlpha by pulseTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.55f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutLinearInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    val timerAlpha = if (lockSeconds <= 30) pulseAlpha else 1f

    Scaffold(
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = darkSurface,
                contentColor = Color.White
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onBought,
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = emerald,
                            contentColor = Color.Black
                        )
                    ) {
                        Text(
                            text = "✓ Bought Player",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }

                    OutlinedButton(
                        onClick = onCanceled,
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.White
                        ),
                        border = BorderStroke(1.dp, Color(0xFF3A4A42))
                    ) {
                        Text(
                            text = "Cancel",
                            fontWeight = FontWeight.Medium,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF050806),
                            Color(0xFF0B0F0C)
                        )
                    )
                )
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = darkSurface,
                border = BorderStroke(1.dp, Color(0xFF163122))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = formatTime(lockSeconds.toLong()),
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = lockTimerColor,
                        modifier = Modifier.clip(RoundedCornerShape(8.dp))
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = darkBg),
                border = BorderStroke(1.dp, Color(0xFF163122))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .width(170.dp)
                                .height(220.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(185.dp, 230.dp)
                                    .background(
                                        brush = Brush.radialGradient(
                                            colors = listOf(
                                                emeraldDim.copy(alpha = 0.35f),
                                                emeraldDim.copy(alpha = 0.08f),
                                                Color.Transparent
                                            )
                                        ),
                                        shape = RoundedCornerShape(22.dp)
                                    )
                            )
                            AsyncImage(
                                model = player.imageUrl,
                                contentDescription = player.playerName,
                                modifier = Modifier
                                    .size(175.dp, 220.dp)
                                    .padding(top = 4.dp),
                                contentScale = ContentScale.Fit
                            )
                            Card(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(6.dp),
                                shape = RoundedCornerShape(999.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF09100B)),
                                border = BorderStroke(1.dp, Color(0xFF163122))
                            ) {
                                Text(
                                    text = player.cardValue.toString(),
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                    color = gold,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }
                        }

                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = player.playerName,
                                style = MaterialTheme.typography.headlineSmall,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )

                            PremiumPill(
                                iconRes = R.drawable.ic_chemistry_flask,
                                text = "${player.chemistryStyle} Chemistry",
                                borderColor = emerald.copy(alpha = 0.55f)
                            )

                            PremiumPill(
                                iconRes = R.drawable.ic_owner,
                                text = "Owners: ${player.owners}",
                                borderColor = emerald.copy(alpha = 0.55f)
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                StatCard(
                                    modifier = Modifier.weight(1f),
                                    title = "Starting Bid",
                                    value = player.startPrice.toString(),
                                    highlightGold = false
                                )
                                StatCard(
                                    modifier = Modifier.weight(1f),
                                    title = "Buy Now",
                                    value = player.buyNowPrice.toString(),
                                    highlightGold = true
                                )
                            }

                            InfoRow(
                                title = "You Earn",
                                value = "$${player.payment}",
                                valueColor = gold
                            )

                            InfoRow(
                                title = "Time Remaining",
                                value = formatTime((player.ea_expires_at ?: 0L)),
                                valueColor = emerald
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PremiumPill(
    iconRes: Int,
    text: String,
    borderColor: Color
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFF09100B),
        border = BorderStroke(1.dp, borderColor)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = painterResource(iconRes),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFE5E7EB),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun StatCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    highlightGold: Boolean
) {
    Surface(
        modifier = modifier.height(76.dp),
        shape = RoundedCornerShape(10.dp),
        color = Color(0xFF090D0A),
        border = BorderStroke(
            1.2.dp,
            if (highlightGold) gold.copy(alpha = 0.85f) else Color(0xFF163122)
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = Color(0xFFB5B8B8)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                color = if (highlightGold) gold else Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun InfoRow(
    title: String,
    value: String,
    valueColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFFB5B8B8)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = valueColor,
            fontWeight = FontWeight.SemiBold
        )
    }
}

private fun formatTime(seconds: Long): String {
    if (seconds <= 0L) return "Expired"
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}