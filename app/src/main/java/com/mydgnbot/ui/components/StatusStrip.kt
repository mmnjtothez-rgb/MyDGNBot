package com.mydgnbot.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
fun StatusStrip(

    platform: String,

    method: String,

    interval: String,

    connected: Boolean,

    onSettingsClick: () -> Unit

) {

    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(24.dp),

        colors = CardDefaults.cardColors(

            containerColor = Color(0xFF11191A)

        ),

        border = BorderStroke(

            1.dp,

            Color(0xFF14C7A3).copy(alpha = 0.18f)

        ),

        elevation = CardDefaults.cardElevation(

            defaultElevation = 8.dp

        )

    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(horizontal = 20.dp),

            verticalAlignment = Alignment.CenterVertically,

            horizontalArrangement = Arrangement.SpaceBetween

        ) {

            Row(

                verticalAlignment = Alignment.CenterVertically,

                horizontalArrangement = Arrangement.spacedBy(22.dp)

            ) {

                StripIcon(

                    if (connected)
                        R.drawable.ic_connected
                    else
                        R.drawable.ic_connected

                )

                StripIcon(

                    if (platform.equals("PC", true))
                        R.drawable.ic_pc
                    else
                        R.drawable.ic_console

                )

                StripIcon(

                    if (method.equals("Safe", true))
                        R.drawable.ic_safe
                    else
                        R.drawable.ic_quicksell

                )

                Row(

                    verticalAlignment = Alignment.CenterVertically

                ) {

                    Image(

                        painter = painterResource(R.drawable.ic_timer),

                        contentDescription = null,

                        modifier = Modifier.size(22.dp)

                    )

                    Spacer(

                        modifier = Modifier.width(6.dp)

                    )

                    Text(

                        text = "${interval}s",

                        color = Color.White,

                        fontWeight = FontWeight.Bold,

                        fontSize = 18.sp

                    )

                }

            }

            Box(

                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF1B2324))
                    .border(

                        BorderStroke(

                            1.dp,

                            Color(0xFF14C7A3).copy(alpha = 0.18f)

                        ),

                        RoundedCornerShape(16.dp)

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

    }

}

@Composable
private fun StripIcon(

    icon: Int

) {

    Image(

        painter = painterResource(icon),

        contentDescription = null,

        modifier = Modifier.size(28.dp)

    )

}