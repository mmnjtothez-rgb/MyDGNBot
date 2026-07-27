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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mydgnbot.R
import com.mydgnbot.ui.theme.Black1
import com.mydgnbot.ui.theme.Emerald
import com.mydgnbot.ui.theme.TextPrimary
import com.mydgnbot.ui.theme.TextSecondary

@Composable
fun StatusStrip(
    platform: String,
    method: String,
    interval: String,
    connected: Boolean,
    onSettingsClick: () -> Unit
) {
    val shape = RoundedCornerShape(18.dp)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 2.dp)
            .drawBehind {
                if (connected) {
                    val glow = Emerald
                    val r = 18.dp.toPx()
                    val w = size.width
                    val h = size.height

                    val topLayers = listOf(0.16f to 18f, 0.11f to 12f, 0.07f to 7f)
                    topLayers.forEach { (alpha, spread) ->
                        drawRoundRect(
                            color = glow.copy(alpha = alpha),
                            topLeft = Offset(-spread, -spread * 1.2f),
                            size = Size(w + spread * 2f, h + spread * 1.8f),
                            cornerRadius = CornerRadius(r + spread * 0.18f, r + spread * 0.18f),
                            blendMode = BlendMode.Screen
                        )
                    }

                    val sideLayers = listOf(0.035f to 12f, 0.020f to 7f)
                    sideLayers.forEach { (alpha, spread) ->
                        drawRoundRect(
                            color = glow.copy(alpha = alpha),
                            topLeft = Offset(-spread, -spread * 0.30f),
                            size = Size(w + spread * 2f, h + spread * 0.85f),
                            cornerRadius = CornerRadius(r + spread * 0.12f, r + spread * 0.12f),
                            blendMode = BlendMode.Screen
                        )
                    }

                    drawRoundRect(
                        color = glow.copy(alpha = 0.02f),
                        topLeft = Offset(0f, 0f),
                        size = Size(w, h),
                        cornerRadius = CornerRadius(r, r),
                        blendMode = BlendMode.Screen
                    )
                }
            }
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = shape,
            colors = CardDefaults.cardColors(containerColor = Black1),
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
                StripIcon(if (connected) R.drawable.ic_connected else R.drawable.ic_connected)
                StripIcon(if (platform.equals("PC", true)) R.drawable.ic_pc else R.drawable.ic_console)
                StripIcon(if (method.equals("Safe", true)) R.drawable.ic_safe else R.drawable.ic_quicksell)

                Image(
                    painter = painterResource(R.drawable.ic_timer),
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )

                Text(
                    text = "${interval}s",
                    color = TextPrimary,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 17.sp
                )

                Spacer(modifier = Modifier.weight(1f))

                SettingsBubble(onSettingsClick = onSettingsClick)
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
private fun SettingsBubble(onSettingsClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .background(Color(0xFF162122), RoundedCornerShape(15.dp))
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