package com.mydgnbot.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mydgnbot.domain.model.Player
import kotlinx.coroutines.delay

@Composable
fun PlayerCard(

    player: Player?

) {

    val context =
        LocalContext.current

    Card(

        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(20.dp)

    ) {

        Column(

            modifier =
                Modifier.padding(16.dp),

            verticalArrangement =
                Arrangement.spacedBy(8.dp)

        ) {

            if (player == null) {

                Text(
                    text =
                        "Waiting for player...",

                    style =
                        MaterialTheme.typography.bodyLarge
                )

                return@Column

            }

            val remainingTime =
                remember(player.marketExpiry) {

                    mutableLongStateOf(
                        player.marketExpiry -
                                (System.currentTimeMillis() / 1000)
                    )

                }

            LaunchedEffect(player.marketExpiry) {

                while (
                    remainingTime.longValue > 0
                ) {

                    delay(1000)

                    remainingTime.longValue =
                        player.marketExpiry -
                                (System.currentTimeMillis() / 1000)

                }

            }

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                verticalAlignment =
                    Alignment.CenterVertically

            ) {

                Text(
    text = "IMAGE: ${player.imageUrl ?: "NULL"}"
)

                    contentDescription =
                        player.playerName,

                    modifier =
                        Modifier.size(110.dp),

                    contentScale =
                        ContentScale.Fit

                )

                Spacer(
                    modifier =
                        Modifier.size(16.dp)
                )

                Column {

                    Text(

                        text =
                            player.playerName,

                        style =
                            MaterialTheme.typography.headlineSmall

                    )

                    Text(

                        text =
                            "Rating ${player.rating}",

                        style =
                            MaterialTheme.typography.titleMedium

                    )

                    Text(

                        text =
                            "Rarity: ${player.rarity ?: "-"}"

                    )

                    Text(

                        text =
                            "Platform: ${player.platform}"

                    )

                }

            }

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween

            ) {

                Column {

                    Text(
                        text =
                            "Starting Bid"
                    )

                    Text(
                        text =
                            "${player.startPrice}"
                    )

                }

                Column {

                    Text(
                        text =
                            "Buy Now"
                    )

                    Text(
                        text =
                            "${player.buyNowPrice}"
                    )

                }

            }

            Text(

                text =
                    "Futbin Price: ${player.cardValue}",

                modifier =
                    Modifier.clickable {

                        val url =
                            "https://www.futbin.com/26/player/${player.assetId}"

                        context.startActivity(

                            Intent(

                                Intent.ACTION_VIEW,

                                Uri.parse(url)

                            )

                        )

                    }

            )

            Text(

                text =
                    "You Earn: $${player.payment}"

            )

            Text(

                text =
                    "Chemistry Style: ${player.chemistryStyle}"

            )

            Text(

                text =
                    "Owners: ${player.owners}"

            )

            Text(

                text =
                    "Expires in: ${
                        formatCountdown(
                            remainingTime.longValue
                        )
                    }"

            )

            Text(

                text =
                    "Transaction ID: ${player.transactionId}"

            )

            Text(

                text =
                    "Trade ID: ${player.tradeId}"

            )

            Text(

                text =
                    "Order: ${
                        readableStatus(
                            player.status
                        )
                    }"

            )

        }

    }

}

private fun formatCountdown(

    seconds: Long

): String {

    if (seconds <= 0) {

        return "Expired"

    }

    val minutes =
        seconds / 60

    val remainingSeconds =
        seconds % 60

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

        "buy" ->
            "Waiting for purchase"

        "bought" ->
            "Completed"

        "cancel" ->
            "Cancelled"

        else ->
            status

    }

}
