package com.mydgnbot.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import com.mydgnbot.R
import com.mydgnbot.ui.theme.Black1
import com.mydgnbot.ui.theme.Emerald
import com.mydgnbot.ui.theme.TextPrimary
import com.mydgnbot.ui.theme.TextSecondary
import kotlinx.coroutines.delay

// Drawable resource IDs for the 14 FC 26 cards
private val fc26CardDrawables: List<Int> = listOf(
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

@Composable
fun BotStatusCard(
    status: BotStatus,
    statusText: String,
    waitSeconds: Int,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val imageLoader = remember {
        ImageLoader.Builder(context)
            .build()
    }

    val currentCardIndex = remember { androidx.compose.runtime.mutableStateOf(0) }
    val scale = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        while (true) {
            scale.animateTo(
                targetValue = 0.85f,
                animationSpec = tween(120)
            )

            currentCardIndex.value = (currentCardIndex.value + 1) % fc26CardDrawables.size

            scale.animateTo(
                targetValue = 1f,
                animationSpec = tween(250)
            )

            delay(1200)
        }
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Black1),
        border = BorderStroke(1.dp, Emerald.copy(alpha = 0.14f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // FC 26 card image sized to ~4 lines of text
            Image(
                painter = painterResource(id = fc26CardDrawables[currentCardIndex.value]),
                contentDescription = "FC 26 Card",
                modifier = Modifier
                    .height(72.dp) // ~4 lines of text
                    .scale(scale.value),
                contentScale = ContentScale.Fit
            )

            Text(
                text = "Bot Status",
                color = TextSecondary,
                style = MaterialTheme.typography.labelSmall
            )

            Text(
                text = statusText,
                color = TextPrimary,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )

            if (waitSeconds > 0) {
                Text(
                    text = "Waiting ${waitSeconds}s",
                    color = Emerald,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}