package com.mydgnbot.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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

        modifier = Modifier.fillMaxWidth()

    ) {

        Column(

            modifier = Modifier.padding(16.dp),

            verticalArrangement = Arrangement.spacedBy(8.dp)

        ) {

            Text(

                text =
                    if (connected)
                        "🟢 Connected"
                    else
                        "🔴 Offline",

                style = MaterialTheme.typography.titleMedium

            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Platform",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = platform,
                    style = MaterialTheme.typography.bodyMedium
                )

            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Method",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = method,
                    style = MaterialTheme.typography.bodyMedium
                )

            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Interval",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "${interval}s",
                    style = MaterialTheme.typography.bodyMedium
                )

            }

        }

    }

}