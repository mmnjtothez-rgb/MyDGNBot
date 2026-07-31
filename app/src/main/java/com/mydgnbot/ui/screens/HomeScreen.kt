package com.mydgnbot.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mydgnbot.domain.model.LogEntry
import com.mydgnbot.ui.viewmodel.HomeViewModel
import java.util.Locale

private val emerald = Color(0xFF42E8B4)
private val darkBg = Color(0xFF0B0F0C)
private val darkSurface = Color(0xFF060807)
private val red = Color(0xFFFF5C5C)

private val CARD_DRAWABLE_NAMES = listOf(
    "fc26_captains_card",
    "fc26_futbirthday_card",
    "fc26_gold_card",
    "fc26_hero_card",
    "fc26_icon_card",
    "fc26_ratingreload_card",
    "fc26_scream_card",
    "fc26_thunderstruck_card",
    "fc26_tots_card",
    "fc26_totw_card",
    "fc26_toty_card",
    "fc26_trophyicon_card",
    "fc26_ucl_card",
    "fc26_winter_card"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onSettingsClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onPlayerFound: () -> Unit
) {
    val isRunning by viewModel.isRunning.collectAsState()
    val isOnline by viewModel.isOnline.collectAsState()
    val logs by viewModel.logs.collectAsState()
    val activePlayer by viewModel.player.collectAsState()
    val settings by viewModel.settings.collectAsState()

    val platform = settings["platform"] ?: "Console"

    val playerType = settings["player_type"] ?: "2"
    val modeText = if (playerType == "1") "Safe" else "Quick Sell"

    val pollSeconds = settings["poll_interval"] ?: "10"
    val interval = "${pollSeconds}s"

    LaunchedEffect(activePlayer) {
        if (activePlayer != null) {
            onPlayerFound()
        }
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
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = darkSurface
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(darkSurface, darkBg)
                    )
                )
                .padding(horizontal = 14.dp, vertical = 6.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Radar Scanner Component
            AnimatedRadarScannerCard(
                isRunning = isRunning,
                connected = isOnline,
                playerFound = activePlayer != null
            )

            // Status Chips Row Component
            AnimatedStatusChipsRow(
                connected = isOnline,
                platform = platform,
                modeText = modeText,
                interval = interval,
                onSettingsClick = onSettingsClick
            )

            Text(
                text = "Live Activity Log",
                style = MaterialTheme.typography.titleSmall,
                color = Color(0xFFB5B8B8),
                fontWeight = FontWeight.SemiBold
            )

            // Live Log Feed + Card Showcase Side-by-Side
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(132.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = darkBg),
                    border = BorderStroke(1.dp, Color(0xFF163122))
                ) {
                    if (logs.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No bot activity yet.",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF6B7280)
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            items(logs.reversed()) { log ->
                                LogItemRow(log = log)
                            }
                        }
                    }
                }

                CardShowcaseBox(
                    modifier = Modifier
                        .width(96.dp)
                        .fillMaxSize()
                )
            }

            // Start Bot Action Buttons
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp)
            ) {
                if (activePlayer != null) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = { viewModel.markBought() },
                            modifier = Modifier.weight(1f).height(42.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = emerald, contentColor = Color.Black)
                        ) {
                            Text("Bought", fontWeight = FontWeight.Bold)
                        }
                        Button(
                            onClick = { viewModel.cancelPlayer() },
                            modifier = Modifier.weight(1f).height(42.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E2923), contentColor = Color.White)
                        ) {
                            Text("Cancel", fontWeight = FontWeight.SemiBold)
                        }
                    }
                }

                Button(
                    onClick = { if (isRunning) viewModel.stopBot() else viewModel.startBot() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isRunning) red else emerald,
                        contentColor = Color.Black
                    )
                ) {
                    Text(
                        text = if (isRunning) "STOP BOT" else "START BOT",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = if (isRunning) Color.White else Color.Black
                    )
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
                // Shifted -1.dp upward to align perfectly with text baseline
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

            DividerText()

            Text(
                text = platform.uppercase(Locale.ROOT),
                style = MaterialTheme.typography.labelLarge,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )

            DividerText()

            Text(
                text = modeText,
                style = MaterialTheme.typography.labelLarge,
                color = emerald,
                fontWeight = FontWeight.Medium
            )

            DividerText()

            Text(
                text = interval,
                style = MaterialTheme.typography.labelLarge,
                color = Color(0xFF9CA3AF),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun DividerText() {
    Text(
        text = " | ",
        color = Color(0xFF27302A),
        style = MaterialTheme.typography.labelLarge,
        modifier = Modifier.padding(horizontal = 6.dp)
    )
}

@Composable
private fun AnimatedRadarScannerCard(
    isRunning: Boolean,
    connected: Boolean,
    playerFound: Boolean
) {
    val infiniteTransition = rememberInfiniteTransition(label = "radar")
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutLinearInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFF070B08),
        border = BorderStroke(1.dp, Color(0xFF163122))
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier.size(56.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val radius = size.minDimension / 2f
                    val centerOffset = Offset(size.width / 2f, size.height / 2f)

                    val outerStroke = 1.5f.dp.toPx()
                    val innerStroke = 1f.dp.toPx()
                    val lineStroke = 2f.dp.toPx()
                    val dotRadius = 4f.dp.toPx()

                    drawCircle(color = Color(0xFF1B3D2B), radius = radius, style = Stroke(width = outerStroke))
                    drawCircle(color = Color(0xFF1B3D2B), radius = radius * 0.65f, style = Stroke(width = innerStroke))
                    drawCircle(color = Color(0xFF1B3D2B), radius = radius * 0.35f, style = Stroke(width = innerStroke))

                    if (isRunning) {
                        rotate(rotationAngle, pivot = centerOffset) {
                            drawLine(
                                brush = Brush.radialGradient(
                                    colors = listOf(emerald.copy(alpha = 0.9f), Color.Transparent),
                                    center = centerOffset,
                                    radius = radius
                                ),
                                start = centerOffset,
                                end = Offset(size.width / 2f, 0f),
                                strokeWidth = lineStroke
                            )
                        }
                    }

                    drawCircle(
                        color = if (isRunning) emerald.copy(alpha = pulseAlpha) else Color.Gray,
                        radius = dotRadius
                    )
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = when {
                        !connected -> "RADAR OFFLINE"
                        playerFound -> "TARGET DETECTED"
                        isRunning -> "SCANNING MYDGN BOARD"
                        else -> "RADAR READY"
                    },
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (playerFound || isRunning) emerald else Color.White
                )
                Text(
                    text = if (isRunning) "Actively polling transfer market" else "Tap Start Bot to launch scanner",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF9CA3AF)
                )
            }
        }
    }
}

@Composable
private fun CardShowcaseBox(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var currentCardIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(3000)
            currentCardIndex = (currentCardIndex + 1) % CARD_DRAWABLE_NAMES.size
        }
    }

    val resName = CARD_DRAWABLE_NAMES[currentCardIndex]
    val resId = remember(currentCardIndex) {
        context.resources.getIdentifier(resName, "drawable", context.packageName)
    }

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        color = Color(0xFF060907),
        border = BorderStroke(1.dp, Color(0xFF163122))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            if (resId != 0) {
                Crossfade(targetState = resId, label = "cardCrossfade") { targetRes ->
                    Image(
                        painter = painterResource(id = targetRes),
                        contentDescription = "Card Showcase",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            } else {
                Text(
                    text = "FC26\nCARD",
                    style = MaterialTheme.typography.labelSmall,
                    color = emerald,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun LogItemRow(log: LogEntry) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(6.dp),
        color = Color(0xFF060907),
        border = BorderStroke(1.dp, Color(0xFF122218))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = log.message,
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFFE5E7EB),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = log.timestamp,
                fontSize = 10.sp,
                color = Color(0xFF6B7280)
            )
        }
    }
}
