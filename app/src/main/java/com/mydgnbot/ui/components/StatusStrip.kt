package com.mydgnbot.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.graphics.Brush
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

    modifier = Modifier
        .fillMaxWidth()
        .border(
            BorderStroke(
                1.dp,
                Color(0xFF14C7A3).copy(alpha = 0.22f)
            ),
            RoundedCornerShape(24.dp)
        ),

    shape = RoundedCornerShape(24.dp),

    colors = CardDefaults.cardColors(

        containerColor = Color(0xFF0C1415)

    ),

    elevation = CardDefaults.cardElevation(

        defaultElevation = 4.dp

    )

) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp)
                .padding(horizontal = 22.dp),

            verticalAlignment = Alignment.CenterVertically,

            horizontalArrangement = Arrangement.Start

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

        modifier = Modifier.size(24.dp)

    )

    Spacer(

        modifier = Modifier.width(8.dp)

    )

    Text(

        text = "${interval}s",

        color = Color(0xFFF2F4F5),

        fontWeight = FontWeight.Bold,

        fontSize = 19.sp

    )

}

            }

Spacer(

    modifier = Modifier.weight(1f)

)

            Box(

    modifier = Modifier
        .size(42.dp)
        .clip(RoundedCornerShape(14.dp))
        .background(Color(0xFF151C1D))
        .border(

            BorderStroke(

                1.dp,

                Color(0xFF14C7A3).copy(alpha = 0.08f)

            ),

            RoundedCornerShape(18.dp)

        )
        .clickable {

            onSettingsClick()

        },

    contentAlignment = Alignment.Center

) {

    Image(

        painter = painterResource(R.drawable.ic_settings),

        contentDescription = "Settings",

        modifier = Modifier.size(21.dp)

    )

}

        }

    }

}

@Composable
private fun StripIcon(

    icon: Int

) {

    Box(

        modifier = Modifier.size(34.dp),

        contentAlignment = Alignment.Center

    ) {

        Image(

            painter = painterResource(icon),

            contentDescription = null,

            modifier = Modifier.size(28.dp)

        )

    }

}