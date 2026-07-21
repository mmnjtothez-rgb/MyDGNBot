package com.mydgnbot.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mydgnbot.domain.model.Player


@Composable
fun PlayerCard(

    player: Player?

) {


    Card(

        modifier = Modifier
            .fillMaxWidth()

    ) {


        Column(

            modifier = Modifier
                .padding(16.dp)

        ) {


            if (player == null) {


                Text(

                    text = "Waiting for player...",

                    style = MaterialTheme.typography.bodyLarge

                )


                return@Column

            }



            Text(

                text = player.playerName,

                style = MaterialTheme.typography.titleLarge

            )



            Spacer(

                modifier = Modifier.height(8.dp)

            )



            Text(

                text = "Rating: ${player.rating}"

            )



            Text(

                text = "Platform: ${player.platform}"

            )



            Spacer(

                modifier = Modifier.height(8.dp)

            )



            Row {

                Column(

                    modifier = Modifier.weight(1f)

                ) {


                    Text(

                        text = "Start Price"

                    )


                    Text(

                        text = player.startPrice.toString()

                    )


                }



                Column(

                    modifier = Modifier.weight(1f)

                ) {


                    Text(

                        text = "Buy Now"

                    )


                    Text(

                        text = player.buyNowPrice.toString()

                    )


                }

            }



            Spacer(

                modifier = Modifier.height(8.dp)

            )



            Text(

                text = "Card Value: ${player.cardValue}"

            )



            Text(

                text = "Payment: $${player.payment}"

            )



            Text(

                text = "Chemistry Style: ${player.chemistryStyle}"

            )



            Text(

                text = "Owners: ${player.owners}"

            )



            Text(

                text = "Market Time: ${player.marketExpiry}"

            )


        }


    }


}
