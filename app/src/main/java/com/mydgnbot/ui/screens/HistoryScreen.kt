package com.mydgnbot.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mydgnbot.domain.model.Player
import com.mydgnbot.ui.theme.Black0
import com.mydgnbot.ui.theme.Black1
import com.mydgnbot.ui.theme.Emerald
import com.mydgnbot.ui.theme.TextPrimary
import com.mydgnbot.ui.theme.TextSecondary

private enum class HistorySort {
    NEWEST,
    RATING,
    BUY_NOW
}

@Composable
fun HistoryScreen(
    players: List<Player>,
    onBackClick: () -> Unit,
    onPlayerClick: (Player) -> Unit
) {
    var sort by remember { mutableStateOf(HistorySort.NEWEST) }
    var filterText by remember { mutableStateOf("All") }
    var sortExpanded by remember { mutableStateOf(false) }
    var filterExpanded by remember { mutableStateOf(false) }

    val filtered = remember(players, filterText, sort) {
        val base = when (filterText) {
            "Bought" -> players.filter { it.status.contains("bought", ignoreCase = true) }
            "Found" -> players.filter { it.status.contains("found", ignoreCase = true) }
            "Cancelled" -> players.filter { it.status.contains("cancel", ignoreCase = true) }
            else -> players
        }

        when (sort) {
            HistorySort.NEWEST -> base.sortedByDescending { it.marketExpiry }
            HistorySort.RATING -> base.sortedByDescending { it.rating }
            HistorySort.BUY_NOW -> base.sortedByDescending { it.buyNowPrice }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Black0
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            HistoryTopBar(onBackClick = onBackClick)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                FilterChipMenu(
                    label = "Sort: ${sort.name.lowercase().replaceFirstChar { it.uppercase() }}",
                    expanded = sortExpanded,
                    onExpandedChange = { sortExpanded = it },
                    options = listOf("Newest", "Rating", "Buy now"),
                    onSelected = {
                        sort = when (it) {
                            "Rating" -> HistorySort.RATING
                            "Buy now" -> HistorySort.BUY_NOW
                            else -> HistorySort.NEWEST
                        }
                    },
                    modifier = Modifier.weight(1f)
                )

                FilterChipMenu(
                    label = "Filter: $filterText",
                    expanded = filterExpanded,
                    onExpandedChange = { filterExpanded = it },
                    options = listOf("All", "Found", "Bought", "Cancelled"),
                    onSelected = { filterText = it },
                    modifier = Modifier.weight(1f)
                )
            }

            Text(
                text = "${filtered.size} players",
                color = TextSecondary,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            if (filtered.isEmpty()) {
                EmptyHistoryState()
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        items = filtered,
                        key = { it.resourceId.ifBlank { it.transactionId } }
                    ) { player ->
                        HistoryPlayerCard(
                            player = player,
                            onClick = { onPlayerClick(player) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HistoryTopBar(
    onBackClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(Color(0xFF0A1110), RoundedCornerShape(12.dp))
                .clickable(onClick = onBackClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Emerald,
                modifier = Modifier.size(18.dp)
            )
        }

        Text(
            text = "History",
            color = TextPrimary,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(start = 10.dp)
        )
    }
}

@Composable
private fun FilterChipMenu(
    label: String,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    options: List<String>,
    onSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        TextButton(
            onClick = { onExpandedChange(true) },
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text(
                text = label,
                color = TextPrimary,
                style = MaterialTheme.typography.labelMedium
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) },
            modifier = Modifier.background(Black1)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option, color = TextPrimary) },
                    onClick = {
                        onSelected(option)
                        onExpandedChange(false)
                    }
                )
            }
        }
    }
}

@Composable
private fun HistoryPlayerCard(
    player: Player,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Black1),
        border = BorderStroke(1.dp, Emerald.copy(alpha = 0.14f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = player.playerName,
                    color = TextPrimary,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = player.rating.toString(),
                    color = Emerald,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = "${player.platform} • ${player.status}",
                color = TextSecondary,
                style = MaterialTheme.typography.bodySmall
            )

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "BIN ${player.buyNowPrice}",
                    color = TextPrimary,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Start ${player.startPrice}",
                    color = TextPrimary,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = player.resourceId,
                    color = TextSecondary,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
private fun EmptyHistoryState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No history yet",
            color = TextSecondary,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}