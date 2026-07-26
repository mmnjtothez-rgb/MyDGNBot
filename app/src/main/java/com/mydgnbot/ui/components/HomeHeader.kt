package com.mydgnbot.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mydgnbot.R

@Composable
fun HomeHeader(

    platform: String,

    method: String,

    interval: String,

    connected: Boolean,

    onSettingsClick: () -> Unit

) {

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = "MyDGNBot",
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.align(Alignment.CenterStart)
        )

        Image(
            painter = painterResource(R.drawable.ic_settings),
            contentDescription = "Settings",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(24.dp)
                .clickable {
                    onSettingsClick()
                }
        )
    }

    Spacer(
        modifier = Modifier.height(12.dp)
    )

    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(24.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF111517)
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )

    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 14.dp),

            horizontalArrangement = Arrangement.SpaceEvenly,

            verticalAlignment = Alignment.CenterVertically

        ) {

            Image(
                painter = painterResource(
                    if (connected)
                        R.drawable.ic_connected
                    else
                        R.drawable.ic_offline
                ),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )

            Divider()

            Image(
                painter = painterResource(
                    if (platform.equals("pc", true))
                        R.drawable.ic_pc
                    else
                        R.drawable.ic_console
                ),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )

            Divider()

            Image(
                painter = painterResource(
                    if (method.equals("Quick Sell", true))
                        R.drawable.ic_quicksell
                    else
                        R.drawable.ic_safe
                ),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )

            Divider()

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(R.drawable.ic_timer),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(
                    modifier = Modifier.size(6.dp)
                )

                Text(
                    text = "${interval}s",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )

            }

        }

    }

}

@Composable
private fun Divider() {

    Box(

        modifier = Modifier
            .height(24.dp)
            .border(
                width = 0.5.dp,
                color = Color.White.copy(alpha = 0.15f)
            )

    )

}