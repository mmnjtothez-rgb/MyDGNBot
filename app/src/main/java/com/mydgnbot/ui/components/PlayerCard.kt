package com.mydgnbot.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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

    // MyDGN already gives us the exact expiry timestamp in marketExpiry.
    val expiresAtMillis = player.marketExpiry * 1000L
    val remainingMillis = remember { mutableStateOf(expiresAtMillis - System.currentTimeMillis()) }
    val isRunning = remember { mutableStateOf(true) }

    LaunchedEffect(player) {
        isRunning.value = true
        while (isRunning.value && remainingMillis.value > 0) {
            delay(1000)
            remainingMillis.value = expiresAtMillis - System.currentTimeMillis()
        }
        if (remainingMillis.value <= 0) {
            onCanceled()
        }
    }

    val remainingSeconds = (remainingMillis.value / 1000).coerceAtLeast(0L).toInt()

    val timerColor by animateColorAsState(
        targetValue = when {
            remainingSeconds > 120 -> emerald
            remainingSeconds > 30 -> amber
            else -> red
        },
        animationSpec = tween(300),
        label = "timerColor"
    )

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutLinearInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    val effectiveTimerAlpha = if (remainingSeconds <= 30) pulseAlpha else 1f

    Scaffold(
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = darkSurface,
                contentColor = Color.White,
                tonalElevation = 8.dp
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
                .fillMaxSize()
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
            // Timer bar
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
                        text = formatTime(remainingSeconds.toLong()),
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = timerColor,
                        modifier = Modifier.graphicsLayer(alpha = effectiveTimerAlpha)
                    )
                }
            }

            // Hero player card block
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = darkBg),
                border = BorderStroke(1.dp, Color(0xFF163122)),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Floating card image with glow
                    Box(
                        modifier = Modifier.fillMaxWidth(),
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
                                .offset(y = (-6).dp),
                            contentScale = ContentScale.Fit
                        )
                    }

                    // Player name + chemistry
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = player.playerName,
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFF09100B),
                            border = BorderStroke(1.dp, emerald.copy(alpha = 0.55f))
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.ic_chemistry_flask),
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "${player.chemistryStyle} Chemistry",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color(0xFFE5E7EB),
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }

                    // Starting Bid / Buy Now
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
                        title = "Payment",
                        value = "$${player.payment}",
                        valueColor = gold
                    )
                    InfoRow(
                        title = "Expires In",
                        value = formatTime(remainingSeconds.toLong()),
                        valueColor = timerColor
                    )
                }
            }
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

private fun formatTime(totalSeconds: Long): String {
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format("%02d:%02d", minutes, seconds)
}