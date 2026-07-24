package com.mydgnbot.ui.components

import androidx.compose.foundation.background
import androidx.compose.ui.unit.IntOffset
import com.mydgnbot.ui.util.ChemistryStyleIcon
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.mydgnbot.R
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mydgnbot.domain.model.Player
import kotlinx.coroutines.delay

@Composable
fun PlayerCard(

    player: Player?

) {

    if (player == null) {

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "Waiting for player...",
                    style = MaterialTheme.typography.titleMedium
                )

            }

        }

        return
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

            Box(

    modifier = Modifier.fillMaxWidth()

) {

                AsyncImage(

                    model = player.imageUrl,

                    contentDescription = player.playerName,

                    modifier = Modifier
                        .size(
                            width = 190.dp,
                            height = 210.dp
                        )
                        .align(Alignment.Center),

                    contentScale = ContentScale.Fit

                )

Surface(

    modifier = Modifier
    .align(Alignment.TopStart)
    .padding(
        top = 6.dp,
        start = 6.dp
    ),

    shape = RoundedCornerShape(18.dp),

    color = Color.Black.copy(alpha = 0.72f)

) {

    Row(

        modifier = Modifier.padding(
            horizontal = 14.dp,
            vertical = 6.dp
        ),

        verticalAlignment = Alignment.CenterVertically

    ) {

        Text(

            text = "⏳",

            style = MaterialTheme.typography.bodyMedium

        )

        Spacer(
            modifier = Modifier.width(6.dp)
        )

        Text(

            text = "04:59",

            style = MaterialTheme.typography.bodyLarge,

            fontWeight = FontWeight.Bold,

            color = Color(0xFF62E37A)

        )

    }

}

                Surface(

    modifier = Modifier
        .align(Alignment.TopEnd)
        .padding(
            top = 6.dp,
            end = 6.dp
        ),

    shape = RoundedCornerShape(18.dp),

    color = Color.Black.copy(alpha = 0.70f)

) {

                    Row(

                        modifier = Modifier.padding(
                            horizontal = 8.dp,
                            vertical = 4.dp
                        ),

                        verticalAlignment = Alignment.CenterVertically

                    ) {

                        AsyncImage(

                            model = "https://www.fut.gg/public-assets/coin.webp",

                            contentDescription = "Coins",

                            modifier = Modifier.size(20.dp)

                        )

                        Spacer(
                            modifier = Modifier.width(4.dp)
                        )

                        Text(

                            text = player.cardValue.toString(),

                            color = Color.White,

                            fontWeight = FontWeight.Bold

                        )

                    }

                }

            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(

                text = player.playerName,

                style = MaterialTheme.typography.headlineSmall,

                fontWeight = FontWeight.Bold

            )

            

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Row(

                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement = Arrangement.spacedBy(12.dp)

            ) {

                PriceCard(

                    modifier = Modifier.weight(1f),

                    title = "Starting Bid",

                    value = player.startPrice.toString()

                )

                PriceCard(

                    modifier = Modifier.weight(1f),

                    title = "Buy Now",

                    value = player.buyNowPrice.toString()

                )

            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )


   Row(

    modifier = Modifier.fillMaxWidth(),

    verticalAlignment = Alignment.CenterVertically

) {

    Image(

        painter = painterResource(R.drawable.ic_chemistry_flask),

        contentDescription = "Chemistry",

        modifier = Modifier.size(22.dp)

    )

    Spacer(
        modifier = Modifier.width(8.dp)
    )

    Text(

        text = "Chemistry",

        style = MaterialTheme.typography.bodyMedium

    )

    Spacer(
        modifier = Modifier.weight(1f)
    )

    AsyncImage(

        model = ChemistryStyleIcon.getUrl(
            player.chemistryStyle
        ),

        contentDescription = player.chemistryStyle,

        modifier = Modifier.size(22.dp),

        contentScale = ContentScale.Fit

    )

    Spacer(
        modifier = Modifier.width(6.dp)
    )

    Text(

        text = player.chemistryStyle,

        style = MaterialTheme.typography.bodyMedium,

        fontWeight = FontWeight.SemiBold

    )

}

Spacer(
    modifier = Modifier.height(10.dp)
)         

            InfoRow(

                title = "Owners",

                value = player.owners.toString()

            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            InfoRow(

                title = "Expires In",

                value = formatCountdown(

                    remainingTime.longValue

                )

            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            InfoRow(

                title = "Payment",

                value = "$${player.payment}"

            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            

        }

    }

}

@Composable
private fun PriceCard(

    modifier: Modifier = Modifier,

    title: String,

    value: String

) {

    Card(

        modifier = modifier,

        shape = RoundedCornerShape(16.dp),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )

    ) {

        Column(

            modifier = Modifier.padding(12.dp),

            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            Text(

                text = title,

                style = MaterialTheme.typography.labelMedium

            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(

                text = value,

                style = MaterialTheme.typography.titleLarge,

                fontWeight = FontWeight.Bold

            )

        }

    }

}

@Composable
private fun InfoRow(

    title: String,

    value: String

) {

    Row(

        modifier = Modifier.fillMaxWidth(),

        horizontalArrangement = Arrangement.SpaceBetween,

        verticalAlignment = Alignment.CenterVertically

    ) {

        Text(

            text = title,

            style = MaterialTheme.typography.bodyMedium

        )

        Text(

            text = value,

            style = MaterialTheme.typography.bodyMedium,

            fontWeight = FontWeight.SemiBold

        )

    }

}

private fun formatCountdown(

    seconds: Long

): String {

    if (seconds <= 0L) {

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