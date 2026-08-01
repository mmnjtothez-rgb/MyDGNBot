package com.mydgnbot.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mydgnbot.domain.model.Player
import com.mydgnbot.ui.theme.Emerald
import com.mydgnbot.ui.theme.TextMuted
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerDetailBottomSheet(
    player: Player,
    onDismiss: () -> Unit,
    onBoughtClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color(0xF007110B),
        scrimColor = Color.Black.copy(alpha = 0.55f),
        dragHandle = {
            Surface(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .width(36.dp)
                    .height(4.dp),
                shape = RoundedCornerShape(2.dp),
                color = Color(0xFF27302A)
            ) {}
        },
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. FORMATTED MYDGN COUNTDOWN TIMER
            val formattedTimer = remember(player.lockExpires) {
                formatCountdownTimer(player.lockExpires)
            }

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .border(BorderStroke(1.dp, Color(0xFF19221C)), RoundedCornerShape(14.dp)),
                color = Color(0xFF0A140F)
            ) {
                Box(
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = formattedTimer,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black,
                        color = Emerald,
                        letterSpacing = 2.sp
                    )
                }
            }

            // PLAYER NAME
            Text(
                text = player.playerName,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            // 2. PLAYER CARD IMAGE & DETAILS ROW
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.Top
            ) {
                // Card Box Container (Size kept original)
                Surface(
                    modifier = Modifier
                        .width(135.dp)
                        .height(185.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.dp, Color(0xFF1E2822), RoundedCornerShape(16.dp)),
                    color = Color(0xFF0D1712)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = player.imageUrl,
                            contentDescription = player.playerName,
                            contentScale = ContentScale.FillBounds, // Image completely fills box space
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(2.dp) // Minimal padding for maximum visual card size
                        )
                    }
                }

                // Side Stats (Chem, Owners, Earnings, Time Left)
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    DetailTile(label = "Chem", value = player.chemistryStyle.ifEmpty { "Basic" })
                    DetailTile(label = "Owners", value = player.owners.ifEmpty { "1" })
                    DetailTile(label = "You Earn", value = "$${player.payment}", valueColor = Emerald)
                    DetailTile(
                        label = "Time Left",
                        value = formatMarketExpiry(player.marketExpiry),
                        valueColor = Emerald
                    )
                }
            }

            // 3. STARTING BID & BUY NOW PRICING CARDS
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Starting Bid
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(14.dp))
                        .border(1.dp, Color(0xFF1E2822), RoundedCornerShape(14.dp)),
                    color = Color(0xFF0D1712)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "STARTING BID",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextMuted
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = player.startPrice,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                // Buy Now
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(14.dp))
                        .border(1.dp, Color(0xFFFFB800), RoundedCornerShape(14.dp)),
                    color = Color(0xFF141A12)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "BUY NOW",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFB800)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = player.buyNowPrice,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFB800)
                        )
                    }
                }
            }

            // 4. ACTION BUTTONS
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Bought Player
                Button(
                    onClick = onBoughtClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Emerald)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Bought Player",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                // Cancel
                Button(
                    onClick = onCancelClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF141A16)),
                    border = BorderStroke(1.dp, Color(0xFF27302A))
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Cancel",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
private fun DetailTile(
    label: String,
    value: String,
    valueColor: Color = Color.White
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .border(1.dp, Color(0xFF19221C), RoundedCornerShape(10.dp)),
        color = Color(0xFF0A140F)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = label, fontSize = 11.sp, color = TextMuted)
            Text(text = value, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = valueColor)
        }
    }
}

private fun formatCountdownTimer(raw: String): String {
    if (raw.isEmpty()) return "00:00"
    val numeric = raw.toLongOrNull() ?: return raw
    return try {
        val totalSeconds = if (numeric > 1000000000L) numeric / 1000 else numeric
        val minutes = TimeUnit.SECONDS.toMinutes(totalSeconds) % 60
        val seconds = totalSeconds % 60
        String.format("%02d:%02d", minutes, seconds)
    } catch (e: Exception) {
        raw
    }
}

private fun formatMarketExpiry(raw: String): String {
    if (raw.isEmpty()) return "59:00"
    val numeric = raw.toLongOrNull() ?: return raw
    val minutes = TimeUnit.SECONDS.toMinutes(numeric)
    val seconds = numeric % 60
    return String.format("%02d:%02d", minutes, seconds)
}
