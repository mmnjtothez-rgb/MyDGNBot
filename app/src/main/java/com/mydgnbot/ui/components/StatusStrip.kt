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
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    connected: Boolean

) {

    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(22.dp),

        colors = CardDefaults.cardColors(

            containerColor = Color(0xFF181B20)

        ),

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

            StripIcon(

                icon =
                if (connected)
                    R.drawable.ic_connected
                else
                    R.drawable.ic_connected

            )

            StripDivider()

            StripIcon(

                icon =
                if (platform.equals("PC", true))
                    R.drawable.ic_pc
                else
                    R.drawable.ic_console

            )

            StripDivider()

            StripIcon(

                icon =
                if (method == "Safe")
                    R.drawable.ic_safe
                else
                    R.drawable.ic_quicksell

            )

            StripDivider()

            Row(

                verticalAlignment = Alignment.CenterVertically

            ) {

                Image(

                    painter = painterResource(R.drawable.ic_timer),

                    contentDescription = null,

                    modifier = Modifier.size(22.dp)

                )

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Text(

                    text = "${interval}s",

                    color = MaterialTheme.colorScheme.onSurface,

                    fontSize = 18.sp,

                    fontWeight = FontWeight.Bold

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

        modifier = Modifier.size(26.dp)

    )

}

@Composable
private fun StripDivider() {

    VerticalDivider(

        modifier = Modifier.height(24.dp),

        color = Color.White.copy(alpha = 0.10f),

        thickness = 1.dp

    )

}