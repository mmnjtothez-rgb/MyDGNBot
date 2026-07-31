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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mydgnbot.domain.model.Player
import com.mydgnbot.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.delay
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

    // 5-minute countdown calculation (MM:ss format)
    var remainingSeconds by remember(player) {
        val now = System.currentTimeMillis() / 1000
        val targetExpiry = player.marketExpiry
        val secondsLeft = if (targetExpiry > now) {
            targetExpiry - now
        } else if (targetExpiry in 1..300) {
            targetExpiry
        } else {
            300L
        }
        mutableLongStateOf(secondsLeft.coerceAtLeast(0L))
    }

    LaunchedEffect(player) {
        while (remainingSeconds > 0) {
            delay(1000L)
            remainingSeconds -= 1
        }
    }

    val minutes = remainingSeconds / 60
    val seconds = remainingSeconds % 60
    val formattedTime = String.format(Locale.US, "%02d:%02d", minutes, seconds)

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
            // Status Chips Row Component
            AnimatedStatusChipsRow(
                connected = isOnline,
                platform = platform,
                modeText = modeText,
                interval = interval,
                onSettingsClick = onSettingsClick
            )

            // Countdown Pill Header
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                color = Color(0xFF040A06),
                border = BorderStroke(1.dp, borderGreen)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = formattedTime,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black,
                        color = emerald,
                        letterSpacing = 1.sp
                    )
                }
            }

            // Main Player Details Card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                color = containerBg,
                border = BorderStroke(1.dp, borderGreen)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Resource ID Badge
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFF142419),
                            border = BorderStroke(1.dp, yellowGold.copy(alpha = 0.6f))
                        ) {
                            Text(
                                text = player.resourceId.ifEmpty { "850" },
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = yellowGold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Player Card Graphic
                    Box(
                        modifier = Modifier
                            .height(200.dp)
                            .width(145.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = player.imageUrl,
                            contentDescription = player.playerName,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Player Full Name
                    Text(
                        text = player.playerName,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Badges (Chem & Owners)
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color(0xFF0B1710),
                            border = BorderStroke(1.dp, borderGreen)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = emerald,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = player.chemistryStyle.ifEmpty { "Basic Chem" },
                                    fontSize = 13.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color(0xFF0B1710),
                            border = BorderStroke(1.dp, borderGreen)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null,
                                    tint = emerald,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Owners: ${player.owners}",
                                    fontSize = 13.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Starting Bid & Buy Now Boxes
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Starting Bid Tile
                        Surface(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFF030905),
                            border = BorderStroke(1.dp, borderGreen)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "Starting Bid",
                                    fontSize = 12.sp,
                                    color = textMuted
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "${player.startPrice}",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }

                        // Buy Now Tile
                        Surface(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFF030905),
                            border = BorderStroke(1.dp, yellowGold)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "Buy Now",
                                    fontSize = 12.sp,
                                    color = yellowGold
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "${player.buyNowPrice}",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = yellowGold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Time Remaining Bottom Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Time Remaining",
                            fontSize = 14.sp,
                            color = textMuted
                        )
                        Text(
                            text = formattedTime,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = emerald
                        )
                    }
                }
            }
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
