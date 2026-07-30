package com.mydgnbot.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mydgnbot.R
import com.mydgnbot.domain.model.Player
import com.mydgnbot.ui.util.ChemistryStyleIcon
import kotlinx.coroutines.delay

@Composable
fun PlayerCard(
    player: Player?
) {
    if (player == null) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0B0F0C)),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF163122))
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

    val remainingTime = remember(player.marketExpiry) {
        mutableLongStateOf(
            player.marketExpiry - (System.currentTimeMillis() / 1000)
        )
    }

    LaunchedEffect(player.marketExpiry) {
        while (remainingTime.longValue > 0) {
            delay(1000)
            remainingTime.longValue = player.marketExpiry - (System.currentTimeMillis() / 1000)
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0B0F0C)),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF163122)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TopTimerCoinBar(
                remainingTime = remainingTime.longValue,
                coinValue = player.cardValue
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                PlayerCardArt(
                    imageUrl = player.imageUrl,
                    playerName = player.playerName
                )

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = player.playerName,
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    MetaPillRow(
                        chemistryStyle = player.chemistryStyle,
                        owners = player.owners
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
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
                        valueColor = Color(0xFFF5C542)
                    )

                    InfoRow(
                        title = "Expires In",
                        value = formatCountdown(remainingTime.longValue),
                        valueColor = Color(0xFF62E37A)
                    )
                }
            }
        }
    }
}

@Composable
private fun TopTimerCoinBar(
    remainingTime: Long,
    coinValue: Int
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFF060807),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF163122))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = formatCountdown(remainingTime),
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF62E37A),
                fontWeight = FontWeight.Bold,
                fontFeatureSettings = "tnum"
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = "https://www.fut.gg/public-assets/coin.webp",
                    contentDescription = "Coins",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = coinValue.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFFF5C542),
                    fontWeight = FontWeight.Bold,
                    fontFeatureSettings = "tnum"
                )
            }
        }
    }
}

@Composable
private fun PlayerCardArt(
    imageUrl: String,
    playerName: String
) {
    Box(
        modifier = Modifier.size(width = 170.dp, height = 210.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF1DD9A2).copy(alpha = 0.35f),
                            Color(0xFF0B0F0C).copy(alpha = 0.0f)
                        )
                    )
                )
        )

        AsyncImage(
            model = imageUrl,
            contentDescription = playerName,
            modifier = Modifier.size(width = 160.dp, height = 205.dp),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
private fun MetaPillRow(
    chemistryStyle: String,
    owners: Int
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TinyPill(
            icon = R.drawable.ic_chemistry_flask,
            label = "Chem",
            value = chemistryStyle
        )
        TinyPill(
            icon = R.drawable.ic_owner,
            label = "Owners",
            value = owners.toString()
        )
    }
}

@Composable
private fun TinyPill(
    icon: Int,
    label: String,
    value: String
) {
    Surface(
        shape = RoundedCornerShape(999.dp),
        color = Color(0xFF09100B),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF163122))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            androidx.compose.foundation.Image(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "$label: $value",
                style = MaterialTheme.typography.bodySmall,
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
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        color = Color(0xFF090D0A),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (highlightGold) Color(0xFFF5C542).copy(alpha = 0.55f) else Color(0xFF163122)
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFFB5B8B8)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                color = if (highlightGold) Color(0xFFF5C542) else Color.White,
                fontWeight = FontWeight.Bold,
                fontFeatureSettings = "tnum"
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
            fontWeight = FontWeight.SemiBold,
            fontFeatureSettings = "tnum"
        )
    }
}

private fun formatCountdown(seconds: Long): String {
    if (seconds <= 0L) return "Expired"
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}
