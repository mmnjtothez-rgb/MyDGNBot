package com.mydgnbot.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.mydgnbot.data.network.ConnectivityObserver
import com.mydgnbot.data.repository.PlayerEnrichmentRepository
import com.mydgnbot.data.repository.PlayerRepository
import com.mydgnbot.data.repository.SettingsRepository
import com.mydgnbot.domain.model.LogEntry
import com.mydgnbot.domain.model.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

enum class HistorySort {
    NEWEST,
    RATING,
    BUY_NOW
}

enum class HistoryFilter {
    ALL,
    FOUND,
    BOUGHT,
    CANCELLED
}

class HomeViewModel(
    private val playerRepository: PlayerRepository,
    private val settingsRepository: SettingsRepository,
    private val playerEnrichmentRepository: PlayerEnrichmentRepository,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {

    private val _player = MutableStateFlow<Player?>(null)
    val player: StateFlow<Player?> = _player.asStateFlow()

    private val _recentPlayers = MutableStateFlow<List<Player>>(emptyList())
    private val _historySort = MutableStateFlow(HistorySort.NEWEST)
    private val _historyFilter = MutableStateFlow(HistoryFilter.ALL)

    val recentPlayers: StateFlow<List<Player>> = combine(
        _recentPlayers,
        _historySort,
        _historyFilter
    ) { players, sort, filter ->
        val filtered = when (filter) {
            HistoryFilter.FOUND -> players.filter { it.status.contains("found", ignoreCase = true) }
            HistoryFilter.BOUGHT -> players.filter { it.status.contains("bought", ignoreCase = true) }
            HistoryFilter.CANCELLED -> players.filter { it.status.contains("cancel", ignoreCase = true) }
            HistoryFilter.ALL -> players
        }

        when (sort) {
            HistorySort.NEWEST -> filtered.sortedByDescending { it.marketExpiry }
            HistorySort.RATING -> filtered.sortedByDescending { it.rating }
            HistorySort.BUY_NOW -> filtered.sortedByDescending { it.buyNowPrice }
        }
    }.let { flow ->
        @Suppress("UNCHECKED_CAST")
        flow as StateFlow<List<Player>>
    }

    private val _showHistory = MutableStateFlow(false)
    val showHistory: StateFlow<Boolean> = _showHistory.asStateFlow()

    private val _logs = MutableStateFlow<List<LogEntry>>(emptyList())
    val logs: StateFlow<List<LogEntry>> = _logs.asStateFlow()

    private val stampFormat = DateTimeFormatter.ofPattern("HH:mm:ss")

    fun requestHistory() {
        _showHistory.value = true
    }

    fun dismissHistory() {
        _showHistory.value = false
    }

    fun setHistorySort(sort: HistorySort) {
        _historySort.value = sort
    }

    fun setHistoryFilter(filter: HistoryFilter) {
        _historyFilter.value = filter
    }

    private fun playerKey(player: Player): String {
        return player.transactionId.ifBlank {
            player.resourceId.ifBlank {
                player.assetId.ifBlank {
                    "${player.baseId}:${player.marketExpiry}"
                }
            }
        }
    }

    private fun addRecentPlayer(player: Player) {
        val key = playerKey(player)
        _recentPlayers.update { current ->
            val filtered = current.filterNot { playerKey(it) == key }
            (listOf(player) + filtered).take(20)
        }
    }

    private fun addLog(message: String) {
        val entry = LogEntry(
            message = message,
            timestamp = LocalDateTime.now().format(stampFormat)
        )
        _logs.update { current -> (current + entry).takeLast(50) }
    }

    fun onPlayerFound(found: Player) {
        _player.value = found
        addRecentPlayer(found)
        addLog("Player found")
    }

    fun startBot() {
        addLog("Bot started")
    }

    fun stopBot() {
        addLog("Bot stopped")
    }

    fun markBought() {
        _player.value?.let { found ->
            addRecentPlayer(found)
            addLog("Bought player")
        }
        _player.value = null
    }

    fun cancelPlayer() {
        _player.value?.let {
            addLog("Cancelled player")
        }
        _player.value = null
    }
}