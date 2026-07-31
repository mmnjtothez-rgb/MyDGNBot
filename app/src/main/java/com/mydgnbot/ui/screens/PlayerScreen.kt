package com.mydgnbot.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mydgnbot.domain.model.Player
import com.mydgnbot.ui.theme.BorderBottom
import com.mydgnbot.ui.theme.BorderTop
import com.mydgnbot.ui.theme.ContainerBgBottom
import com.mydgnbot.ui.theme.ContainerBgTop
import com.mydgnbot.ui.theme.DarkBg
import com.mydgnbot.ui.theme.Emerald
import com.mydgnbot.ui.theme.EmeraldDark
import com.mydgnbot.ui.theme.EmeraldGlow
import com.mydgnbot.ui.theme.TextMuted
import java.util.Locale

@Composable
fun PlayerScreen(
    player: Player,
    platform: String,
    playerType: String,
    pollInterval: String,
    timeRemainingText: String,
    onBoughtClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    val modeText = if (playerType == "1") "Safe" else "Quick Sell"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(EmeraldGlow, DarkBg),
                    center = Offset(350f, 150f),
                    radius = 1100f
                )
            )
    ) {
        Scaffold(
            containerColor = Color.Transparent
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 1. TOP HEADER STATUS BAR
                TopStatusHeader(
                    platform = platform,
                    modeText = modeText,
                    interval = "${pollInterval}s"
                )

                // 2. COUNTDOWN TIMER BANNER
                GlassmorphicCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = timeRemainingText,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Black,
                            color = Emerald,
                            letterSpacing = 2.sp
                        )
                    }
                }

                // 3. MAIN PLAYER DETAILS CARD CONTAINER
                GlassmorphicCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Player Name Banner
                        Text(
                            text = player.playerName,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )

                        // Center Layout: Image vs Details Grid
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Left Side: Player Image Frame
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(180.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color(0xFF0D1410))
                                    .border(1.dp, Color(0xFF1E2822), RoundedCornerShape(16.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                if (!player.imageUrl.isNullOrEmpty()) {
                                    AsyncImage(
                                        model = player.imageUrl,
                                        contentDescription = player.playerName,
                                        contentScale = ContentScale.Fit,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(8.dp)
                                    )
                                } else {
                                    Text(
                                        text = "${player.rating}\n${player.position ?: ""}",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Emerald,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }

                            // Right Side: Quick Specs Stack
                            Column(
                                modifier = Modifier.weight(1.1f),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                SpecItemCard(
                                    label = "Chem",
                                    value = if (player.chemistryStyle.isEmpty()) "Basic" else player.chemistryStyle,
                                    icon = Icons.Default.Star
                                )
                                SpecItemCard(
                                    label = "Owners",
                                    value = player.owners.toString(),
                                    icon = Icons.Default.Person
                                )
                                SpecItemCard(
                                    label = "You Earn",
                                    value = "$${String.format(Locale.US, "%.2f", player.payment)}",
                                    valueColor = Emerald
                                )
                                SpecItemCard(
                                    label = "Time Left",
                                    value = timeRemainingText,
                                    valueColor = Emerald
                                )
                            }
                        }

                        // Price Bidding & Buy Now Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // Starting Bid Box
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(Color(0xFF0F1512))
                                    .border(1.dp, Color(0xFF1F2923), RoundedCornerShape(14.dp))
                                    .padding(vertical = 12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "STARTING BID",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextMuted,
                                        letterSpacing = 0.5.sp
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = String.format(Locale.US, "%,d", player.startPrice),
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }

                            // Buy Now Box
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(Color(0xFF131A14))
                                    .border(1.dp, Color(0xFFEAB308).copy(alpha = 0.6f), RoundedCornerShape(14.dp))
                                    .padding(vertical = 12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "BUY NOW",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFEAB308),
                                        letterSpacing = 0.5.sp
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = String.format(Locale.US, "%,d", player.buyNowPrice),
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFEAB308)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        // Action Buttons Inside Container
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // Bought Player Button
                            val boughtShape = RoundedCornerShape(14.dp)
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                                    .shadow(6.dp, boughtShape, ambientColor = Emerald, spotColor = Emerald)
                                    .clip(boughtShape)
                                    .background(Brush.horizontalGradient(listOf(Emerald, EmeraldDark)))
                                    .clickable { onBoughtClick() },
                                contentAlignment = Alignment.Center
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Bought",
                                        tint = Color.Black,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Bought Player",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = Color.Black
                                    )
                                }
                            }

                            // Cancel Button
                            val cancelShape = RoundedCornerShape(14.dp)
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                                    .clip(cancelShape)
                                    .background(Color(0xFF121915))
                                    .border(1.dp, Color(0xFF233027), cancelShape)
                                    .clickable { onCancelClick() },
                                contentAlignment = Alignment.Center
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Cancel",
                                        tint = Color.White,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Cancel",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SpecItemCard(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector? = null,
    valueColor: Color = Color.White
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFF0E1411))
            .border(1.dp, Color(0xFF1E2721), RoundedCornerShape(10.dp))
            .padding(horizontal = 10.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                icon?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        tint = Emerald,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
                Text(
                    text = label,
                    fontSize = 11.sp,
                    color = TextMuted
                )
            }
            Text(
                text = value,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = valueColor
            )
        }
    }
}

@Composable
private fun TopStatusHeader(
    platform: String,
    modeText: String,
    interval: String
) {
    val shape = RoundedCornerShape(16.dp)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(Color(0xFF0B100D))
            .border(1.dp, Color(0xFF18221B), shape)
            .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(androidx.compose.foundation.shape.CircleShape)
                        .background(Emerald)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "CONNECTED",
                    style = MaterialTheme.typography.labelMedium,
                    color = Emerald,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Text(text = "|", color = Color(0xFF27302A), fontSize = 12.sp)
            Text(
                text = platform.uppercase(Locale.ROOT),
                style = MaterialTheme.typography.labelMedium,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
            Text(text = "|", color = Color(0xFF27302A), fontSize = 12.sp)
            Text(
                text = modeText,
                style = MaterialTheme.typography.labelMedium,
                color = Emerald,
                fontWeight = FontWeight.Medium
            )
            Text(text = "|", color = Color(0xFF27302A), fontSize = 12.sp)
            Text(
                text = interval,
                style = MaterialTheme.typography.labelMedium,
                color = Color(0xFF9CA3AF),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun GlassmorphicCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val shape = RoundedCornerShape(18.dp)
    Surface(
        modifier = modifier
            .clip(shape)
            .background(
                Brush.verticalGradient(
                    colors = listOf(ContainerBgTop, ContainerBgBottom)
                )
            )
            .border(
                border = BorderStroke(
                    1.dp,
                    Brush.verticalGradient(
                        colors = listOf(BorderTop, BorderBottom)
                    )
                ),
                shape = shape
            ),
        color = Color.Transparent
    ) {
        content()
    }
}
