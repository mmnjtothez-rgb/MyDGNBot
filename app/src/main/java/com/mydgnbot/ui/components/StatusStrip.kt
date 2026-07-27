package com.mydgnbot.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mydgnbot.R

@Composable
fun StatusStrip(

    platform: String,

    method: String,

    interval: String,

    connected: Boolean,

    onSettingsClick: () -> Unit

) {

    Box(

        modifier = Modifier.fillMaxWidth()

    ) {

        Box(

    modifier = Modifier
        .fillMaxWidth()
        .height(82.dp)
        .padding(horizontal = 6.dp)
        .offset(y = 12.dp)
        .clip(RoundedCornerShape(30.dp))
        .background(

            Brush.horizontalGradient(

                colors = listOf(

                    Color.Transparent,

                    Color(0xFF00FFC6).copy(alpha = 0.10f),

                    Color(0xFF00FFC6).copy(alpha = 0.14f),

                    Color(0xFF00FFC6).copy(alpha = 0.10f),

                    Color.Transparent

                )

            )

        )

)

Box(

    modifier = Modifier
        .fillMaxWidth()
        .height(96.dp)
        .padding(horizontal = 18.dp)
        .offset(y = 16.dp)
        .clip(RoundedCornerShape(40.dp))
        .background(

            Color(0xFF00FFC6).copy(alpha = 0.025f)

        )

)

        Card(

            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(24.dp),

            colors = CardDefaults.cardColors(

                containerColor = Color(0xFF101818)

            ),

            border = BorderStroke(

                1.dp,

                Color(0xFF16F2C2).copy(alpha = .18f)

            ),

            elevation = CardDefaults.cardElevation(

                defaultElevation = 0.dp

            )

        ) {

            Row(

                modifier = Modifier
                    .fillMaxWidth()
                    .height(68.dp)
                    .padding(horizontal = 22.dp),

                verticalAlignment = Alignment.CenterVertically

            ) {

                StripIcon(

                    if (connected)
                        R.drawable.ic_connected
                    else
                        R.drawable.ic_connected

                )

                Spacer(Modifier.width(26.dp))

                StripIcon(

                    if (platform.equals("PC", true))
                        R.drawable.ic_pc
                    else
                        R.drawable.ic_console

                )

                Spacer(Modifier.width(26.dp))

                StripIcon(

                    if (method == "Safe")
                        R.drawable.ic_safe
                    else
                        R.drawable.ic_quicksell

                )

                Spacer(Modifier.width(26.dp))

                Image(

                    painter = painterResource(R.drawable.ic_timer),

                    contentDescription = null,

                    modifier = Modifier.size(24.dp)

                )

                Spacer(Modifier.width(8.dp))

                Text(

                    text = "${interval}s",

                    color = Color.White,

                    fontWeight = FontWeight.Bold,

                    fontSize = 18.sp

                )

                Spacer(

                    modifier = Modifier.weight(1f)

                )

                SettingsBubble(

                    onSettingsClick

                )

            }

        }

    }

}

@Composable
private fun StripIcon(

    icon: Int

) {

    Image(

        painter = painterResource(icon),

        contentDescription = null,

        modifier = Modifier.size(30.dp)

    )

}

@Composable
private fun SettingsBubble(

    onSettingsClick: () -> Unit

) {

    Box(

        modifier = Modifier
            .size(46.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(

                Brush.verticalGradient(

                    listOf(

                        Color(0xFF1A2425),

                        Color(0xFF131B1C)

                    )

                )

            )
            .border(

                BorderStroke(

                    1.dp,

                    Color(0xFF18F7C8).copy(alpha = .15f)

                ),

                RoundedCornerShape(15.dp)

            )
            .clickable {

                onSettingsClick()

            },

        contentAlignment = Alignment.Center

    ) {

        Image(

            painter = painterResource(R.drawable.ic_settings),

            contentDescription = "Settings",

            modifier = Modifier.size(22.dp)

        )

    }

}