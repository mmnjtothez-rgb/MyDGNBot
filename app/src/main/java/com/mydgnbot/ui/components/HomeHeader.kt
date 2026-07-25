package com.mydgnbot.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mydgnbot.R

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

            HeaderIcon(

                icon =
                if (connected)
                    R.drawable.ic_connected
                else
                    R.drawable.ic_connected

            )

            Divider()

            HeaderIcon(

                icon =
                if (platform.equals("PC", true))
                    R.drawable.ic_pc
                else
                    R.drawable.ic_console

            )

            Divider()

            HeaderIcon(

                icon =
                if (method.contains("Safe", true))
                    R.drawable.ic_safe
                else
                    R.drawable.ic_quicksell

            )

            Divider()

            Row(

                verticalAlignment = Alignment.CenterVertically

            ) {

                HeaderIcon(

                    icon = R.drawable.ic_timer

                )

                Spacer(
                    modifier = Modifier.width(6.dp)
                )

                Text(

                    text = "${interval}s",

                    color = Color.White,

                    style = MaterialTheme.typography.bodyMedium,

                    fontWeight = FontWeight.SemiBold

                )

            }

        }

    }

}

@Composable
private fun HeaderIcon(

    icon: Int

) {

    Image(

        painter = painterResource(icon),

        contentDescription = null,

        modifier = Modifier.size(22.dp)

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