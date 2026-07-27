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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
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
    val shape = RoundedCornerShape(22.dp)
    val glow = Color(0xFF18E6BE)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 2.dp)
            .drawBehind {
                if (connected) {
                    drawRoundRect(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                glow.copy(alpha = 0.28f),
                                glow.copy(alpha = 0.10f),
                                Color.Transparent
                            ),
                            radius = size.minDimension * 1.15f
                        ),
                        topLeft = Offset(-12f, -10f),
                        size = Size(size.width + 24f, size.height + 20f),
                        cornerRadius = CornerRadius(22.dp.toPx(), 22.dp.toPx())
                    )
                }
            }
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = shape,
            colors = CardDefaults.cardColors(containerColor = Color(0xFF111718)),
            border = BorderStroke(
                1.dp,
                if (connected) glow.copy(alpha = 0.28f) else Color.White.copy(alpha = 0.06f)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(68.dp)
                    .padding(horizontal = 18.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                StripIcon(
                    icon = if (connected) R.drawable.ic_connected else R.drawable.ic_connected
                )

                StripIcon(
                    icon = if (platform.equals("PC", true)) R.drawable.ic_pc else R.drawable.ic_console
                )

                StripIcon(
                    icon = if (method.equals("Safe", true)) R.drawable.ic_safe else R.drawable.ic_quicksell
                )

                Image(
                    painter = painterResource(R.drawable.ic_timer),
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )

                Text(
                    text = "${interval}s",
                    color = Color(0xFFF3F6F7),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.weight(1f))

                SettingsBubble(
                    onSettingsClick = onSettingsClick,
                    glow = glow
                )
            }
        }
    }
}

@Composable
private fun StripIcon(icon: Int) {
    Image(
        painter = painterResource(icon),
        contentDescription = null,
        modifier = Modifier.size(30.dp)
    )
}

@Composable
private fun SettingsBubble(
    onSettingsClick: () -> Unit,
    glow: Color
) {
    Box(
        modifier = Modifier
            .size(46.dp)
            .background(Color(0xFF162122), RoundedCornerShape(16.dp))
            .border(
                BorderStroke(1.dp, glow.copy(alpha = 0.16f)),
                RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onSettingsClick),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_settings),
            contentDescription = "Settings",
            modifier = Modifier.size(22.dp)
        )
    }
}