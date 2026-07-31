package com.mydgnbot.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mydgnbot.R
import com.mydgnbot.domain.model.Player
import com.mydgnbot.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.delay
import java.text.NumberFormat
import java.util.Locale

private val emerald = Color(0xFF42E8B4)
private val darkBg = Color(0xFF000000)
private val containerBg = Color(0xFF07120B)
private val borderGreen = Color(0xFF163122)
private val yellowGold = Color(0xFFFBBF24)
private val textMuted = Color(0xFF9CA3AF)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(
    viewModel: HomeViewModel,
    onBackClick: () -> Unit,
    player: Player,
    onSettingsClick: () -> Unit = {},
    onHistoryClick: () -> Unit = {}
) {
    val isOnline by viewModel.isOnline.collectAsState()
    val settings by viewModel.settings.collectAsState()

    val platform = settings["platform"] ?: player.platform.name
    val playerType = settings["player_type"] ?: "2"
    val modeText = if (playerType == "1") "Safe" else "Quick Sell"
    val pollSeconds = settings["poll_interval"] ?: "10"
    val interval = "${pollSeconds}s"

    // 1. MyDGN Lock Timer (Handles Epoch Timestamp or Relative Seconds)
    var lockRemainingSeconds by remember(player) {
        val now = System.currentTimeMillis() / 1000
        val targetLock = player.lockExpires
        
        val secondsLeft = when {
            targetLock > 1_000_000_000L -> targetLock - now // Unix Epoch Timestamp
            targetLock > 0L -> targetLock                  // Relative Remaining Seconds
            else -> 300L                             // 5-minute fallback
        }
        mutableLongStateOf(secondsLeft.coerceAtLeast(0L))
    }

    // 2. EA Market Expiry Timer (marketExpiry / ea_expires_at)
    var marketRemainingSeconds by remember(player) {
        val now = System.currentTimeMillis() / 1000
        val targetMarket = player.marketExpiry
        
        val secondsLeft = when {
            targetMarket > 1_000_000_000L -> targetMarket - now
            targetMarket > 0L -> targetMarket
            else -> 3600L
        }
        mutableLongStateOf(secondsLeft.coerceAtLeast(0L))
    }

    LaunchedEffect(player) {
        while (lockRemainingSeconds > 0 || marketRemainingSeconds > 0) {
            delay(1000L)
            if (lockRemainingSeconds > 0) lockRemainingSeconds -= 1
            if (marketRemainingSeconds > 0) marketRemainingSeconds -= 1
        }
    }

    val lockMinutes = lockRemainingSeconds / 60
    val lockSecs = lockRemainingSeconds % 60
    val formattedLockTimer = String.format(Locale.US, "%02d:%02d", lockMinutes, lockSecs)

    val formattedMarketTime = remember(marketRemainingSeconds) {
        val days = marketRemainingSeconds / 86400
        val hours = (marketRemainingSeconds % 86400) / 3600
        val minutes = (marketRemainingSeconds % 3600) / 60
        val seconds = marketRemainingSeconds % 60

        when {
            days > 0 -> "${days}d ${hours}h"
            hours > 0 -> "${hours}h ${minutes}m"
            else -> String.format(Locale.US, "%02d:%02d", minutes, seconds)
        }
    }

    val formattedCardValue = remember(player.cardValue) {
        NumberFormat.getNumberInstance(Locale.US).format(player.cardValue)
    }

    val formattedPayment = remember(player.payment) {
        if (player.payment > 0) "$${player.payment}" else "$0.00"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "MYDGN",
                            fontWeight = FontWeight.Black,
                            fontSize = 20.sp,
                            color = emerald
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "BOT",
                            fontWeight = FontWeight.Light,
                            fontSize = 20.sp,
                            color = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onHistoryClick) {
                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = "History",
                            tint = Color(0xFFE5E7EB)
                        )
                    }
                    IconButton(onClick = onSettingsClick) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = Color(0xFFE5E7EB)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = darkBg)
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(darkBg)
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        viewModel.markBought()
                        onBackClick()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = emerald,
                        contentColor = Color.Black
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Bought Player",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }

                Button(
                    onClick = {
                        viewModel.cancelPlayer()
                        onBackClick()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color(0xFF27352B)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0F1712),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Cancel",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(darkBg)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 14.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AnimatedStatusChipsRow(
                connected = isOnline,
                platform = platform,
                modeText = modeText,
                interval = interval,
                onSettingsClick = onSettingsClick
            )

            // Top Header MyDGN Lock Countdown Pill
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                color = Color(0xFF040A06),
                border = BorderStroke(1.dp, borderGreen)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = formattedLockTimer,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black,
                        color = emerald,
                        letterSpacing = 1.sp
                    )
                }
            }

            // Main Container Card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                color = containerBg,
                border = BorderStroke(1.dp, borderGreen)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Player Header Title
                    Text(
                        text = player.playerName,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    // Side-by-Side Main Content Layout
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // LEFT: Player Card Frame
                        Box(
                            modifier = Modifier
                                .width(155.dp)
                                .height(210.dp),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            AsyncImage(
                                model = player.imageUrl,
                                contentDescription = player.playerName,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 22.dp)
                            )

                            // CardValue Badge shifted 12dp down onto card top
                            Surface(
                                modifier = Modifier.offset(y = 12.dp),
                                shape = RoundedCornerShape(12.dp),
                                color = Color(0xFF0C1D13),
                                border = BorderStroke(1.dp, yellowGold)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.valuebadge),
                                        contentDescription = "Value Badge",
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text(
                                        text = formattedCardValue,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = yellowGold
                                    )
                                }
                            }
                        }

                        // RIGHT: Specs Stack
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            DetailRowTile(
                                icon = Icons.Default.Star,
                                iconColor = emerald,
                                label = "Chem",
                                value = player.chemistryStyle.ifEmpty { "Basic Chem" }
                            )

                            DetailRowTile(
                                icon = Icons.Default.Person,
                                iconColor = emerald,
                                label = "Owners",
                                value = "${player.owners}"
                            )

                            DetailRowTile(
                                label = "You Earn",
                                value = formattedPayment,
                                valueColor = emerald
                            )

                            DetailRowTile(
                                label = "Time Remaining",
                                value = formattedMarketTime,
                                valueColor = emerald
                            )
                        }
                    }

                    // Starting Bid & Buy Now Cards
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(14.dp))
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(Color(0xFF09140D), Color(0xFF030805))
                                    )
                                )
                                .border(1.dp, Color(0xFF163122), RoundedCornerShape(14.dp))
                                .padding(12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "STARTING BID",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = textMuted,
                                    letterSpacing = 0.5.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = NumberFormat.getNumberInstance(Locale.US).format(player.startPrice),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(14.dp))
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(Color(0xFF1C1A09), Color(0xFF070601))
                                    )
                                )
                                .border(1.dp, yellowGold.copy(alpha = 0.8f), RoundedCornerShape(14.dp))
                                .padding(12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "BUY NOW",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = yellowGold.copy(alpha = 0.8f),
                                    letterSpacing = 0.5.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = NumberFormat.getNumberInstance(Locale.US).format(player.buyNowPrice),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Black,
                                    color = yellowGold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailRowTile(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector? = null,
    iconColor: Color = emerald,
    valueColor: Color = Color.White
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        color = Color(0xFF0A160F),
        border = BorderStroke(1.dp, borderGreen)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                }
                Text(
                    text = label,
                    fontSize = 11.sp,
                    color = textMuted,
                    fontWeight = FontWeight.Medium
                )
            }
            Text(
                text = value,
                fontSize = 12.sp,
                color = valueColor,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun AnimatedStatusChipsRow(
    connected: Boolean,
    platform: String,
    modeText: String,
    interval: String,
    onSettingsClick: () -> Unit
) {
    val shape = RoundedCornerShape(20.dp)

    val infiniteTransition = rememberInfiniteTransition(label = "connectedPulse")
    val dotAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dotPulse"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(Color(0xFF050805))
            .border(1.dp, Color(0xFF141A16), shape)
            .padding(horizontal = 14.dp, vertical = 10.dp)
            .clickable(onClick = onSettingsClick)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Box(
                    modifier = Modifier
                        .offset(y = (-1).dp)
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(
                            if (connected) emerald.copy(alpha = dotAlpha) else Color.Gray
                        )
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (connected) "CONNECTED" else "OFFLINE",
                    style = MaterialTheme.typography.labelLarge,
                    color = if (connected) emerald else Color.Gray,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Text(
                text = " | ",
                color = Color(0xFF27302A),
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(horizontal = 6.dp)
            )

            Text(
                text = platform.uppercase(Locale.ROOT),
                style = MaterialTheme.typography.labelLarge,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = " | ",
                color = Color(0xFF27302A),
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(horizontal = 6.dp)
            )

            Text(
                text = modeText,
                style = MaterialTheme.typography.labelLarge,
                color = emerald,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = " | ",
                color = Color(0xFF27302A),
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(horizontal = 6.dp)
            )

            Text(
                text = interval,
                style = MaterialTheme.typography.labelLarge,
                color = Color(0xFF9CA3AF),
                fontWeight = FontWeight.Medium
            )
        }
    }
}
