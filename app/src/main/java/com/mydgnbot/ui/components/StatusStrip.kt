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
import androidx.compose.ui.draw.shadow
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

        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 2.dp)

    ) {

        Card(

            modifier = Modifier
                .fillMaxWidth()
                .shadow(

                    elevation = 18.dp,

                    shape = RoundedCornerShape(24.dp),

                    ambientColor = Color(0xFF18E6BE).copy(alpha = 0.18f),

                    spotColor = Color(0xFF18E6BE).copy(alpha = 0.22f)

                ),

            shape = RoundedCornerShape(24.dp),

            colors = CardDefaults.cardColors(

                containerColor = Color(0xFF101717)

            ),

            border = BorderStroke(

                1.dp,

                Color(0xFF18E6BE).copy(alpha = 0.18f)

            ),

            elevation = CardDefaults.cardElevation(

                defaultElevation = 0.dp

            )

        ) {

            Box(

                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(

                        Color.White.copy(alpha = 0.05f)

                    )

            )

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

                Spacer(

                    modifier = Modifier.width(22.dp)

                )

                StripIcon(

                    if (platform.equals("PC", true))

                        R.drawable.ic_pc

                    else

                        R.drawable.ic_console

                )

                Spacer(

                    modifier = Modifier.width(22.dp)

                )

                StripIcon(

                    if (method.equals("Safe", true))

                        R.drawable.ic_safe

                    else

                        R.drawable.ic_quicksell

                )

                Spacer(

                    modifier = Modifier.width(22.dp)

                )

                Image(

                    painter = painterResource(

                        R.drawable.ic_timer

                    ),

                    contentDescription = null,

                    modifier = Modifier.size(22.dp)

                )

                Spacer(

                    modifier = Modifier.width(8.dp)

                )

                Text(

                    text = "${interval}s",

                    color = Color(0xFFF3F6F7),

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
            .background(

                Color(0xFF172122),

                RoundedCornerShape(16.dp)

            )
            .border(

                BorderStroke(

                    1.dp,

                    Color(0xFF18E6BE).copy(alpha = 0.16f)

                ),

                RoundedCornerShape(16.dp)

            )
            .clickable {

                onSettingsClick()

            },

        contentAlignment = Alignment.Center

    ) {

        Box(

            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(

                    Color.White.copy(alpha = 0.04f)

                )
                .align(Alignment.TopCenter)

        )

        Image(

            painter = painterResource(

                R.drawable.ic_settings

            ),

            contentDescription = "Settings",

            modifier = Modifier.size(22.dp)

        )

    }

}