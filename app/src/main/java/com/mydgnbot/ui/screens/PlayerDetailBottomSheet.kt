package com.mydgnbot.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mydgnbot.domain.model.Player
import com.mydgnbot.ui.theme.*
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerDetailBottomSheet(
    player: Player,
    onDismiss: () -> Unit,
    onBoughtClick: (Player) -> Unit,
    onCancelClick: (Player) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = DarkBg,
        scrimColor = Color.Black.copy(alpha = 0.7f),
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .width(36.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color(0xFF27302A))
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 1. TOP HEADER: LOCK EXPIRY TIMER (Formatted mm:ss)
            val formattedLockExpiry = remember(player.lockExpiry) {
                formatDurationSeconds(player.lockExpiry)
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF06140C))
                    .border(1.dp, Emerald.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = formattedLockExpiry,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black,
                    color = Emerald,
                    letterSpacing = 1.sp
                )
            }

            // 2. PLAYER NAME
            Text(
                text = player.playerName ?: "Unknown Player",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            // 3. MIDDLE SECTION: CARD WITH VALUE BADGE + METRICS
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // LEFT: Player Card Image + Integrated Value Badge Overlay
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(180.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF0F1612))
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
                    }

                    // INTEGRATED CARD VALUE BADGE (Top Center Pill)
                    if (player.cardValue != null && player.cardValue > 0) {
                        Surface(
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .padding(top = 8.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .border(1.dp, Color(0xFFF59E0B), RoundedCornerShape(20.dp)),
                            color = Color.Black.copy(alpha = 0.85f)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(14.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFF59E0B)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "FVT",
                                        fontSize = 5.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Color.Black
                                    )
                                }
                                Text(
                                    text = "${player.cardValue}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFF59E0B)
                                )
                            }
                        }
                    }
                }

                // RIGHT: METRICS (Chem, Owners, You Earn, Market Time Remaining)
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MetricChip(
                        icon = { Icon(Icons.Default.Star, contentDescription = null, tint = Emerald, modifier = Modifier.size(14.dp)) },
                        label = "Chem",
                        value = player.chem ?: "Basic"
                    )

                    MetricChip(
                        icon = { Icon(Icons.Default.Person, contentDescription = null, tint = Emerald, modifier = Modifier.size(14.dp)) },
                        label = "Owners",
                        value = "${player.owners ?: 1}"
                    )

                    MetricChip(
                        label = "You Earn",
                        value = "$${String.format(Locale.US, "%.3f", player.earnings ?: 0.0)}",
                        valueColor = Emerald
                    )

                    // Market Expiry formatting (Fixed timestamp / duration)
                    val formattedMarketTime = remember(player.timeRemaining) {
                        formatMarketTimeRemaining(player.timeRemaining)
                    }

                    MetricChip(
                        label = "Time Remaining",
                        value = formattedMarketTime,
                        valueColor = Emerald
                    )
                }
            }

            // 4. BIDDING PRICES
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                PriceTile(
                    modifier = Modifier.weight(1f),
                    title = "STARTING BID",
                    price = "${player.startingBid ?: 0}",
                    borderColor = Color(0xFF1E2822)
                )

                PriceTile(
                    modifier = Modifier.weight(1f),
                    title = "BUY NOW",
                    price = "${player.buyNow ?: 0}",
                    borderColor = Color(0xFFF59E0B),
                    priceColor = Color(0xFFF59E0B)
                )
            }

            // 5. ACTION BUTTONS
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { onBoughtClick(player) },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Emerald)
                ) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Bought Player",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontSize = 13.sp
                    )
                }

                OutlinedButton(
                    onClick = { onCancelClick(player) },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color(0xFF27302A)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
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
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

// HELPER COMPOSABLES
@Composable
private fun MetricChip(
    label: String,
    value: String,
    icon: (@Composable () -> Unit)? = null,
    valueColor: Color = Color.White
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .border(1.dp, Color(0xFF19221C), RoundedCornerShape(10.dp)),
        color = Color(0xFF0B120E)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                icon?.invoke()
                Text(text = label, fontSize = 11.sp, color = TextMuted)
            }
            Text(text = value, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = valueColor)
        }
    }
}

@Composable
private fun PriceTile(
    modifier: Modifier = Modifier,
    title: String,
    price: String,
    borderColor: Color,
    priceColor: Color = Color.White
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0xFF09120D))
            .border(1.dp, borderColor, RoundedCornerShape(14.dp))
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = title, fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = TextMuted)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = price, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = priceColor)
        }
    }
}

// TIME FORMATTING HELPERS
private fun formatDurationSeconds(seconds: Long?): String {
    if (seconds == null || seconds <= 0) return "00:00"
    val mins = seconds / 60
    val secs = seconds % 60
    return String.format(Locale.US, "%02d:%02d", mins, secs)
}

private fun formatMarketTimeRemaining(timeValue: Any?): String {
    if (timeValue == null) return "N/A"
    
    val rawString = timeValue.toString()
    val number = rawString.toLongOrNull() ?: return rawString

    // If it's a huge epoch timestamp in seconds/millis
    return if (number > 100_000_000) {
        val diffSeconds = (number - (System.currentTimeMillis() / 1000)).coerceAtLeast(0)
        val mins = diffSeconds / 60
        val secs = diffSeconds % 60
        String.format(Locale.US, "%02d:%02d", mins, secs)
    } else {
        // If it's remaining seconds directly
        val mins = number / 60
        val secs = number % 60
        String.format(Locale.US, "%02d:%02d", mins, secs)
    }
}
