package com.mydgnbot.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.mydgnbot.ui.viewmodel.HomeViewModel
import com.mydgnbot.ui.viewmodel.HistoryFilter
import com.mydgnbot.ui.viewmodel.HistorySort

@Composable
fun HistoryScreen(
    viewModel: HomeViewModel,
    onBackClick: () -> Unit,
    onPlayerClick: (Player) -> Unit
) {
    val players by viewModel.recentPlayers.collectAsState()
    val sortState by viewModel.currentHistorySort.collectAsState()
    val filterState by viewModel.currentHistoryFilter.collectAsState()

    val sortIndex = when (sortState) {
        HistorySort.NEWEST -> 0
        HistorySort.RATING -> 1
        HistorySort.BUY_NOW -> 2
    }

    val filterIndex = when (filterState) {
        HistoryFilter.ALL -> 0
        HistoryFilter.FOUND -> 1
        HistoryFilter.BOUGHT -> 2
        HistoryFilter.CANCELLED -> 3
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

            Text(
                text = "Latest fetched players",
                color = TextSecondary,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
            )

            SegmentedRow(
                title = "Sort",
                options = listOf("Newest", "Rating", "Buy Now"),
                selectedIndex = sortIndex,
                onSelected = { index ->
                    viewModel.setHistorySort(
                        when (index) {
                            1 -> HistorySort.RATING
                            2 -> HistorySort.BUY_NOW
                            else -> HistorySort.NEWEST
                        }
                    )
                }
            )

            SegmentedRow(
                title = "Filter",
                options = listOf("All", "Found", "Bought", "Cancelled"),
                selectedIndex = filterIndex,
                onSelected = { index ->
                    viewModel.setHistoryFilter(
                        when (index) {
                            1 -> HistoryFilter.FOUND
                            2 -> HistoryFilter.BOUGHT
                            3 -> HistoryFilter.CANCELLED
                            else -> HistoryFilter.ALL
                        }
                    )
                },
                topPadding = 10.dp,
                bottomPadding = 12.dp
            )

            Text(
                text = "${players.size} players",
                color = TextSecondary,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            if (players.isEmpty()) {
                EmptyHistoryState()
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        items = players,
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
private fun SegmentedRow(
    title: String,
    options: List<String>,
    selectedIndex: Int,
    onSelected: (Int) -> Unit,
    topPadding: androidx.compose.ui.unit.Dp = 0.dp,
    bottomPadding: androidx.compose.ui.unit.Dp = 0.dp
) {
    Column(
        modifier = Modifier.padding(top = topPadding, bottom = bottomPadding),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            color = TextSecondary,
            style = MaterialTheme.typography.labelSmall
        )

        SingleChoiceSegmentedButtonRow {
            options.forEachIndexed { index, label ->
                SegmentedButton(
                    selected = index == selectedIndex,
                    onClick = { onSelected(index) },
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                    colors = SegmentedButtonDefaults.colors(
                        activeContainerColor = Emerald,
                        activeContentColor = Color.Black,
                        inactiveContainerColor = Black1,
                        inactiveContentColor = TextPrimary
                    )
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelMedium
                    )
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