package com.mydgnbot.ui.components

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun HomeHeader(

    platform: String,

    method: String,

    interval: String,

    connected: Boolean = true

) {


    Card(

        modifier = Modifier
            .fillMaxWidth()

    ) {


        Column(

            modifier = Modifier
                .padding(16.dp)

        ) {


            Row(

                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement = Arrangement.SpaceBetween,

                verticalAlignment = Alignment.CenterVertically

            ) {


                Text(

                    text = "MyDGNBot",

                    style = MaterialTheme.typography.titleLarge

                )


                Text(

                    text =
                        if (connected)
                            "🟢 Connected"
                        else
                            "🔴 Offline",

                    style = MaterialTheme.typography.bodyMedium

                )


            }



            Spacer(

                modifier = Modifier.height(12.dp)

            )



            Text(

                text = "Platform: $platform",

                style = MaterialTheme.typography.bodyMedium

            )



            Text(

                text = "Method: $method",

                style = MaterialTheme.typography.bodyMedium

            )



            Text(

                text = "Interval: ${interval}s",

                style = MaterialTheme.typography.bodyMedium

            )


        }


    }


}
