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
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mydgnbot.R
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
import com.mydgnbot.ui.viewmodel.HomeViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onHistoryClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onPlayerClick: (Player) -> Unit
) {
    val isRunning by viewModel.isRunning.collectAsState()
    val isOnline by viewModel.isOnline.collectAsState()
    val settings by viewModel.settings.collectAsState()
    val logs by viewModel.logs.collectAsState()
    val latestPlayer by viewModel.latestPlayer.collectAsState()

    val platform = settings["platform"] ?: "CONSOLE"
    val playerType = settings["player_type"] ?: "2"
    val modeText = if (playerType == "1") "Safe" else "Quick Sell"
    val pollSeconds = settings["poll_interval"] ?: "10"
    val interval = "${pollSeconds}s"

    // Background Gradient with Top Radial Glow
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
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "MYDGN",
                                fontWeight = FontWeight.Black,
                                fontSize = 20.sp,
                                color = Emerald
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
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 14.dp, vertical = 6.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // 1. RADAR STATUS CARD (Live pulsing radar ring)
                GlassmorphicCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AnimatedRadarUnit(isRunning = isRunning)
                        
                        Spacer(modifier = Modifier.width(16.dp))
                        
                        Column {
                            Text(
                                text = if (isRunning) "RADAR ACTIVE" else "RADAR READY",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = if (isRunning) "Scanning EA FC market..." else "Tap Start Bot to launch scanner",
                                fontSize = 12.sp,
                                color = TextMuted
                            )
                        }
                    }
                }

                // 2. STATUS CHIPS ROW
                AnimatedStatusChipsRow(
                    connected = isOnline,
                    platform = platform,
                    modeText = modeText,
                    interval = interval,
                    onSettingsClick = onSettingsClick
                )

                // 3. LIVE ACTIVITY LOG & PLAYER SLOT SECTION
                Text(
                    text = "Live Activity Log",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    modifier = Modifier.padding(start = 2.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // LEFT: Activity Log Box
                    GlassmorphicCard(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            contentAlignment = if (logs.isEmpty()) Alignment.Center else Alignment.TopStart
                        ) {
                            if (logs.isEmpty()) {
                                Text(
                                    text = "No bot activity yet.",
                                    fontSize = 13.sp,
                                    color = TextMuted
                                )
                            } else {
                                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                    logs.takeLast(6).forEach { log ->
                                        Text(
                                            text = log,
                                            fontSize = 11.sp,
                                            color = Color(0xFFD1D5DB)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // RIGHT: Player Card Preview Frame
                    GlassmorphicCard(
                        modifier = Modifier
                            .width(115.dp)
                            .fillMaxSize()
                            .clickable(enabled = latestPlayer != null) {
                                latestPlayer?.let { onPlayerClick(it) }
                            }
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            if (latestPlayer?.imageUrl != null) {
                                AsyncImage(
                                    model = latestPlayer!!.imageUrl,
                                    contentDescription = latestPlayer!!.playerName,
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(6.dp)
                                )
                            } else {
                                Image(
                                    painter = painterResource(id = R.drawable.card_placeholder),
                                    contentDescription = "Card Placeholder",
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(6.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // 4. METALLIC "START BOT" CTA BUTTON
                Button(
                    onClick = { viewModel.toggleBot() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .shadow(
                            elevation = if (isRunning) 0.dp else 10.dp,
                            shape = RoundedCornerShape(16.dp),
                            ambientColor = Emerald,
                            spotColor = Emerald
                        ),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.horizontalGradient(
                                    if (isRunning) listOf(Color(0xFFDC2626), Color(0xFF991B1B))
                                    else listOf(Emerald, EmeraldDark)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (isRunning) "STOP BOT" else "START BOT",
                            fontWeight = FontWeight.Black,
                            fontSize = 16.sp,
                            color = if (isRunning) Color.White else Color.Black,
                            letterSpacing = 1.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun GlassmorphicCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val shape = RoundedCornerShape(20.dp)
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

@Composable
private fun AnimatedRadarUnit(isRunning: Boolean) {
    val infiniteTransition = rememberInfiniteTransition(label = "RadarPulse")
    
    val ringScale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.35f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ringScale"
    )

    val ringAlpha by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ringAlpha"
    )

    Box(
        modifier = Modifier.size(52.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isRunning) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .scale(ringScale)
                    .clip(CircleShape)
                    .border(1.dp, Emerald.copy(alpha = ringAlpha), CircleShape)
            )
        }

        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(Color(0xFF07170E))
                .border(1.dp, Emerald.copy(alpha = 0.4f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .border(1.dp, Emerald.copy(alpha = 0.7f), CircleShape)
            )
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(Emerald)
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
                            if (connected) Emerald.copy(alpha = dotAlpha) else Color.Gray
                        )
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (connected) "CONNECTED" else "OFFLINE",
                    style = MaterialTheme.typography.labelLarge,
                    color = if (connected) Emerald else Color.Gray,
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
                color = Emerald,
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
