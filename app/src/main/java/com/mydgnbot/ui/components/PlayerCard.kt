package com.mydgnbot.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mydgnbot.domain.model.Player
import kotlinx.coroutines.delay

@Composable
fun PlayerCard(
    player: Player?
) {

    val context = LocalContext.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            if (player == null) {

                Text("Waiting for player...")

                return@Column
            }

            val remainingTime = remember(player.marketExpiry) {

                mutableLongStateOf(
                    player.marketExpiry -
                            (System.currentTimeMillis() / 1000)
                )

            }

            LaunchedEffect(player.marketExpiry) {

                while (remainingTime.longValue > 0) {

                    delay(1000)

                    remainingTime.longValue =
                        player.marketExpiry -
                                (System.currentTimeMillis() / 1000)

                }

            }

            Row(
                verticalAlignment = Alignment.Top
            ) {

                Box {

                    AsyncImage(
                        model = player.imageUrl,
                        contentDescription = player.playerName,
                        modifier = Modifier.size(
                            width = 150.dp,
                            height = 170.dp
                        ),
                        contentScale = ContentScale.Fit
                    )

                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                Color.Black.copy(alpha = 0.65f)
                            )
                            .padding(
                                horizontal = 8.dp,
                                vertical = 4.dp
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        AsyncImage(
                            model = "https://www.fut.gg/public-assets/coin.webp",
                            contentDescription = "Coins",
                            modifier = Modifier.size(18.dp)
                        )

                        Spacer(
                            modifier = Modifier.size(4.dp)
                        )

                        Text(
                            text = player.cardValue.toString(),
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )

                    }

                }

                Spacer(
                    modifier = Modifier.size(16.dp)
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {

                    Text(
                        text = player.playerName,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    if (!player.rarity.isNullOrBlank()) {

                        Text(
                            text = player.rarity,
                            style = MaterialTheme.typography.bodyMedium
                        )

                    }

                }

            }

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(14.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            "Starting Bid",
                            style = MaterialTheme.typography.labelMedium
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        Text(
                            text = player.startPrice.toString(),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )

                    }

                }

                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(14.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            "Buy Now",
                            style = MaterialTheme.typography.labelMedium
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        Text(
                            text = player.buyNowPrice.toString(),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )

                    }

                }

            }

            Spacer(
                modifier = Modifier.height(18.dp)
            )            infoRow(
                "Chemistry Style",
                player.chemistryStyle
            )

            infoRow(
                "Owners",
                player.owners.toString()
            )

            infoRow(
                "Expires In",
                formatCountdown(
                    remainingTime.longValue
                )
            )

            infoRow(
                "Payment",
                "$${player.payment}"
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = "Transaction ID",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = player.transactionId,
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Trade ID",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = player.tradeId,
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = readableStatus(
                    player.status
                ),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )

        }

    }

}

@Composable
private fun infoRow(

    title: String,

    value: String

) {

    Row(

        modifier = Modifier.fillMaxWidth(),

        horizontalArrangement =
            Arrangement.SpaceBetween,

        verticalAlignment =
            Alignment.CenterVertically

    ) {

        Text(

            text = title,

            style =
                MaterialTheme.typography.bodyMedium

        )

        Text(

            text = value,

            style =
                MaterialTheme.typography.bodyMedium,

            fontWeight =
                FontWeight.SemiBold

        )

    }

}

private fun formatCountdown(

    seconds: Long

): String {

    if (seconds <= 0) {

        return "Expired"

    }

    val minutes = seconds / 60

    val remainingSeconds = seconds % 60

    return String.format(

        "%02d:%02d",

        minutes,

        remainingSeconds

    )

}

private fun readableStatus(

    status: String

): String {

    return when (

        status.lowercase()

    ) {

        "buy" -> "Waiting for purchase"

        "bought" -> "Completed"

        "cancel" -> "Cancelled"

        else -> status

    }

}