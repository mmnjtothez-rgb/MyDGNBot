package com.mydgnbot.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HomeHeader(

    platform: String,

    method: String,

    interval: String,

    connected: Boolean = true

) {

    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(18.dp),

        colors = CardDefaults.cardColors(

            containerColor = Color(0xFF181C1F)

        )

    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp
                ),

            horizontalArrangement = Arrangement.SpaceEvenly,

            verticalAlignment = Alignment.CenterVertically

        ) {

            HeaderItem(

                text =
                if (connected)
                    "● CONNECTED"
                else
                    "● OFFLINE",

                color =
                if (connected)
                    Color(0xFF21D07A)
                else
                    Color.Red

            )

            Divider()

            HeaderItem(
                text = platform
            )

            Divider()

            HeaderItem(
                text = method
            )

            Divider()

            HeaderItem(
                text = "${interval}s"
            )

        }

    }

}

@Composable
private fun HeaderItem(

    text: String,

    color: Color = Color.White

) {

    Text(

        text = text,

        color = color,

        style = MaterialTheme.typography.bodyMedium,

        fontWeight = FontWeight.SemiBold

    )

}

@Composable
private fun Divider() {

    Box(

        modifier = Modifier
            .width(1.dp)
            .height(18.dp)
            .background(
                Color(0xFF2E3338)
            )

    )

}