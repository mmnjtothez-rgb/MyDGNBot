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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.CornerRadius
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
    val shape = RoundedCornerShape(18.dp)
    val emerald = Color(0xFF3DFFB8)
    val deepBg = Color(0xFF101617)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 2.dp)
            .drawBehind {
                if (connected) {
                    drawRoundRect(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                emerald.copy(alpha = 0.00f),
                                emerald.copy(alpha = 0.85f),
                                emerald.copy(alpha = 1.00f),
                                emerald.copy(alpha = 0.85f),
                                emerald.copy(alpha = 0.00f)
                            )
                        ),
                        topLeft = Offset(-1f, -1f),
                        size = Size(size.width + 2f, size.height + 2f),
                        cornerRadius = CornerRadius(18.dp.toPx(), 18.dp.toPx())
                    )

                    drawRoundRect(
                        color = emerald.copy(alpha = 0.12f),
                        topLeft = Offset(1.5f, 1.5f),
                        size = Size(size.width - 3f, size.height - 3f),
                        cornerRadius = CornerRadius(16.dp.toPx(), 16.dp.toPx())
                    )
                }
            }
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = shape,
            colors = CardDefaults.cardColors(containerColor = deepBg),
            border = BorderStroke(
                1.dp,
                if (connected) emerald.copy(alpha = 0.98f) else emerald.copy(alpha = 0.20f)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(68.dp)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
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
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 17.sp
                )

                Spacer(modifier = Modifier.weight(1f))

                SettingsBubble(
                    onSettingsClick = onSettingsClick,
                    emerald = emerald
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
    emerald: Color
) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .background(Color(0xFF162122), RoundedCornerShape(15.dp))
            .border(
                BorderStroke(1.dp, emerald.copy(alpha = 0.26f)),
                RoundedCornerShape(15.dp)
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