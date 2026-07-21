package com.mydgnbot.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mydgnbot.domain.model.Player


@Composable
fun PlayerCard(

    player: Player?

) {


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



            // FC style header

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                verticalAlignment =
                    Alignment.CenterVertically

            ) {


                AsyncImage(

                    model =
                        player.imageUrl,

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



                    Spacer(

                        modifier =
                            Modifier.height(4.dp)

                    )



                    Text(

                        text =
                            "Rating ${player.rating}",

                        style =
                            MaterialTheme.typography.titleMedium

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



            // Prices

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



            Spacer(

                modifier =
                    Modifier.height(8.dp)

            )



            Text(

                text =
                    "MyDGN Value: ${player.cardValue}"

            )


            Text(

                text =
                    "Payment: $${player.payment}"

            )



            Spacer(

                modifier =
                    Modifier.height(8.dp)

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
                    "Market Expiry: ${player.marketExpiry}"

            )



            Spacer(

                modifier =
                    Modifier.height(8.dp)

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
                    "Status: ${player.status}"

            )


        }

    }

}
