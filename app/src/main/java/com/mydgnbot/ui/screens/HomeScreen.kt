package com.mydgnbot.ui.screens

import androidx.compose.animation.Crossfade
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import com.mydgnbot.ui.theme.Black1
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

private val CARD_BACKGROUNDS = listOf(
    R.drawable.fc26_captains_card,
    R.drawable.fc26_futbirthday_card,
    R.drawable.fc26_gold_card,
    R.drawable.fc26_hero_card,
    R.drawable.fc26_icon_card,
    R.drawable.fc26_ratingreload_card,
    R.drawable.fc26_scream_card,
    R.drawable.fc26_thunderstruck_card,
    R.drawable.fc26_tots_card,
    R.drawable.fc26_totw_card,
    R.drawable.fc26_toty_card,
    R.drawable.fc26_trophyicon_card,
    R.drawable.fc26_ucl_card,
    R.drawable.fc26_winter_card
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onHistoryClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onPlayerClick: (Player) -> Unit = {}
) {
    val isRunning by viewModel.isRunning.collectAsState()
    val isOnline by viewModel.isOnline.collectAsState()
    val settings by viewModel.settings.collectAsState()
    val logs by viewModel.logs.collectAsState()
    val activePlayer by viewModel.player.collectAsState()

    var isSheetOpen by remember { mutableStateOf(false) }

    val currentPlatform = settings["platform"] ?: "CONSOLE"
    val currentPlayerType = settings["player_type"] ?: "2"
    val modeText = if (currentPlayerType == "1") "Safe" else "Quick Sell"
    val currentPollInterval = settings["poll_interval"] ?: "10"
    val intervalText = "${currentPollInterval}s"

    var bgIndex by remember { mutableIntStateOf(0) }
    LaunchedEffect(Unit) {
        if (CARD_BACKGROUNDS.isNotEmpty()) {
            while (true) {
                delay(3000)
                bgIndex = (bgIndex + 1) % CARD_BACKGROUNDS.size
            }
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "TapHintTransition")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    val cardGlowScale by infiniteTransition.animateFloat(
        initialValue = 0.98f,
        targetValue = 1.03f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "cardGlowScale"
    )

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
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 1. RADAR STATUS CARD
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
                                fontSize = 16.sp,
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

                // 2. STATUS CHIPS
                AnimatedStatusChipsRow(
                    connected = isOnline,
                    platform = currentPlatform,
                    modeText = modeText,
                    interval = intervalText,
                    onSettingsClick = onSettingsClick
                )

                // 3. LIVE ACTIVITY LOG & CARD SLOT
                Text(
                    text = "Live Activity Log",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    modifier = Modifier.padding(start = 2.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(161.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val logScrollState = rememberScrollState()

                    LaunchedEffect(logs.size) {
                        if (logs.isNotEmpty()) {
                            logScrollState.animateScrollTo(logScrollState.maxValue)
                        }
                    }

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
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .verticalScroll(logScrollState),
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    logs.forEach { log ->
                                        Row(verticalAlignment = Alignment.Top) {
                                            Text(
                                                text = "[${log.timestamp}] ",
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Emerald
                                            )
                                            Text(
                                                text = log.message,
                                                fontSize = 11.sp,
                                                color = Color(0xFFD1D5DB)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    val isPlayerActive = activePlayer != null
                    val shape = RoundedCornerShape(18.dp)

                    Box(
                        modifier = Modifier
                            .width(110.dp)
                            .fillMaxSize()
                            .scale(if (isPlayerActive) cardGlowScale else 1f)
                    ) {
                        GlassmorphicCard(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable(enabled = isPlayerActive) {
                                    isSheetOpen = true
                                    activePlayer?.let { onPlayerClick(it) }
                                }
                                .then(
                                    if (isPlayerActive) {
                                        Modifier.border(
                                            width = 1.5.dp,
                                            color = Emerald.copy(alpha = pulseAlpha),
                                            shape = shape
                                        )
                                    } else Modifier
                                )
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                val imgUrl = activePlayer?.imageUrl
                                if (!imgUrl.isNullOrEmpty()) {
                                    AsyncImage(
                                        model = imgUrl,
                                        contentDescription = activePlayer?.playerName ?: "Player Image",
                                        contentScale = ContentScale.Fit,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(bottom = 20.dp, top = 6.dp, start = 6.dp, end = 6.dp)
                                    )

                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.BottomCenter)
                                            .padding(bottom = 6.dp)
                                            .clip(RoundedCornerShape(20.dp))
                                            .background(Color(0xFF0A2016).copy(alpha = 0.9f))
                                            .border(
                                                width = 1.dp,
                                                color = Emerald.copy(alpha = pulseAlpha),
                                                shape = RoundedCornerShape(20.dp)
                                            )
                                            .padding(horizontal = 8.dp, vertical = 3.dp)
                                    ) {
                                        Text(
                                            text = "TAP TO VIEW",
                                            fontSize = 8.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Emerald.copy(alpha = pulseAlpha),
                                            letterSpacing = 0.5.sp
                                        )
                                    }
                                } else {
                                    Crossfade(
                                        targetState = CARD_BACKGROUNDS[bgIndex],
                                        animationSpec = tween(durationMillis = 800),
                                        label = "CardBgTransition"
                                    ) { resId ->
                                        Image(
                                            painter = painterResource(id = resId),
                                            contentDescription = "Card Background",
                                            contentScale = ContentScale.Fit,
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(8.dp)
                                                .alpha(0.9f)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // 4. START / STOP BOT BUTTON
                val buttonShape = RoundedCornerShape(14.dp)
                val buttonBg = if (isRunning) {
                    Brush.horizontalGradient(listOf(Color(0xFFDC2626), Color(0xFF991B1B)))
                } else {
                    Brush.horizontalGradient(listOf(Emerald, EmeraldDark))
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .shadow(
                            elevation = if (isRunning) 0.dp else 12.dp,
                            shape = buttonShape,
                            ambientColor = Emerald,
                            spotColor = Emerald
                        )
                        .clip(buttonShape)
                        .background(buttonBg)
                        .clickable {
                            if (isRunning) viewModel.stopBot() else viewModel.startBot()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (isRunning) "STOP BOT" else "START BOT",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = if (isRunning) Color.White else Color.Black,
                        letterSpacing = 1.sp
                    )
                }

                // 5. QUICK CONTROLS
                Text(
                    text = "Quick Controls",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    modifier = Modifier.padding(start = 2.dp)
                )

                GlassmorphicCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Target Platform",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = "Active market ecosystem",
                                    fontSize = 11.sp,
                                    color = TextMuted
                                )
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                listOf("CONSOLE", "PC").forEach { platform ->
                                    val isSelected = currentPlatform.equals(platform, ignoreCase = true)
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(if (isSelected) Emerald else Color(0xFF141A16))
                                            .border(
                                                1.dp,
                                                if (isSelected) Emerald else Color(0xFF27302A),
                                                RoundedCornerShape(8.dp)
                                            )
                                            .clickable {
                                                viewModel.saveSetting("platform", platform)
                                            }
                                            .padding(horizontal = 12.dp, vertical = 6.dp)
                                    ) {
                                        Text(
                                            text = platform,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isSelected) Color.Black else Color.White
                                        )
                                    }
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Player Strategy",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = "Safe filtering vs quick sell",
                                    fontSize = 11.sp,
                                    color = TextMuted
                                )
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                listOf("Safe" to "1", "Quick Sell" to "2").forEach { (label, modeCode) ->
                                    val isSelected = currentPlayerType == modeCode
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(if (isSelected) Emerald else Color(0xFF141A16))
                                            .border(
                                                1.dp,
                                                if (isSelected) Emerald else Color(0xFF27302A),
                                                RoundedCornerShape(8.dp)
                                            )
                                            .clickable {
                                                viewModel.saveSetting("player_type", modeCode)
                                            }
                                            .padding(horizontal = 10.dp, vertical = 6.dp)
                                    ) {
                                        Text(
                                            text = label,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isSelected) Color.Black else Color.White
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // 6. BOTTOM SHEET OVERLAY
        if (isSheetOpen && activePlayer != null) {
            PlayerDetailBottomSheet(
                player = activePlayer!!,
                onDismiss = {
                    isSheetOpen = false
                },
                onBoughtClick = {
                    isSheetOpen = false
                    viewModel.markBought()
                },
                onCancelClick = {
                    isSheetOpen = false
                    viewModel.cancelPlayer()
                }
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
        modifier = Modifier.size(48.dp),
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
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFF07170E))
                .border(1.dp, Emerald.copy(alpha = 0.4f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .border(1.dp, Emerald.copy(alpha = 0.7f), CircleShape)
            )
            Box(
                modifier = Modifier
                    .size(6.dp)
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
    val shape = RoundedCornerShape(16.dp)

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
            .background(Black1)
            .border(1.dp, Color(0xFF141A16), shape)
            .padding(horizontal = 14.dp, vertical = 10.dp)
            .clickable(onClick = onSettingsClick)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
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
                    style = MaterialTheme.typography.labelMedium,
                    color = if (connected) Emerald else Color.Gray,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Text(
                text = "|",
                color = Color(0xFF27302A),
                fontSize = 12.sp
            )

            Text(
                text = platform.uppercase(Locale.ROOT),
                style = MaterialTheme.typography.labelMedium,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = "|",
                color = Color(0xFF27302A),
                fontSize = 12.sp
            )

            Text(
                text = modeText,
                style = MaterialTheme.typography.labelMedium,
                color = Emerald,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = "|",
                color = Color(0xFF27302A),
                fontSize = 12.sp
            )

            Text(
                text = interval,
                style = MaterialTheme.typography.labelMedium,
                color = Color(0xFF9CA3AF),
                fontWeight = FontWeight.Medium
            )
        }
    }
}
