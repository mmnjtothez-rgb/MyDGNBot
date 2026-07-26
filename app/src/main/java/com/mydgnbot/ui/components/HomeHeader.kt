package com.mydgnbot.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Divider
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

    Column {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "MyDGNBot",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(1f)
            )

            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF1A1D22))
                    .clickable {
                        onSettingsClick()
                    },
                contentAlignment = Alignment.Center
            ) {

                Image(
                    painter = painterResource(R.drawable.ic_settings),
                    contentDescription = "Settings",
                    modifier = Modifier.size(24.dp)
                )

            }

        }

        Spacer(modifier = Modifier.height(18.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF181B20)
            ),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.dp
            )
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                HeaderItem(
                    icon = if (connected)
                        R.drawable.ic_connected
                    else
                        R.drawable.ic_connected,
                    text = ""
                )

                Divider(
                    modifier = Modifier
                        .height(26.dp)
                        .padding(horizontal = 6.dp),
                    color = Color.White.copy(alpha = 0.10f)
                )

                HeaderItem(
                    icon = if (platform.equals("PC", true))
                        R.drawable.ic_pc
                    else
                        R.drawable.ic_console,
                    text = ""
                )

                Divider(
                    modifier = Modifier
                        .height(26.dp)
                        .padding(horizontal = 6.dp),
                    color = Color.White.copy(alpha = 0.10f)
                )

                HeaderItem(
                    icon = if (method == "Safe")
                        R.drawable.ic_safe
                    else
                        R.drawable.ic_quicksell,
                    text = ""
                )

                Divider(
                    modifier = Modifier
                        .height(26.dp)
                        .padding(horizontal = 6.dp),
                    color = Color.White.copy(alpha = 0.10f)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Image(
                        painter = painterResource(R.drawable.ic_timer),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )

                    Spacer(modifier = Modifier.size(8.dp))

                    Text(
                        text = "${interval}s",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                }

            }

        }

    }

}

@Composable
private fun HeaderItem(
    icon: Int,
    text: String
) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(28.dp)
        )

        if (text.isNotEmpty()) {

            Spacer(modifier = Modifier.size(6.dp))

            Text(
                text = text,
                color = Color.White,
                fontSize = 16.sp
            )

        }

    }

}