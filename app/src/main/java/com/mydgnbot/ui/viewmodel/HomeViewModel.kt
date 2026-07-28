package com.mydgnbot.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mydgnbot.data.network.ConnectivityObserver
import com.mydgnbot.data.repository.PlayerEnrichmentRepository
import com.mydgnbot.data.repository.PlayerRepository
import com.mydgnbot.data.repository.SettingsRepository
import com.mydgnbot.domain.model.LogEntry
import com.mydgnbot.domain.model.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

    // Current player
    private val _player = MutableStateFlow<Player?>(null)
    val player: StateFlow<Player?> = _player.asStateFlow()

    // Recently fetched players for History
    private val _recentPlayers = MutableStateFlow<List<Player>>(emptyList())

    private val _historySort = MutableStateFlow(HistorySort.NEWEST)
    val currentHistorySort: StateFlow<HistorySort> = _historySort.asStateFlow()

    private val _historyFilter = MutableStateFlow(HistoryFilter.ALL)
    val currentHistoryFilter: StateFlow<HistoryFilter> = _historyFilter.asStateFlow()

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
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    // Whether History bottom sheet / screen is requested
    private val _showHistory = MutableStateFlow(false)
    val showHistory: StateFlow<Boolean> = _showHistory.asStateFlow()

    // Activity logs
    private val _logs = MutableStateFlow<List<LogEntry>>(emptyList())
    val logs: StateFlow<List<LogEntry>> = _logs.asStateFlow()

    private val stampFormat = DateTimeFormatter.ofPattern("HH:mm:ss")

    // Settings as a Map<String, String>
    val settings: StateFlow<Map<String, String>> =
        settingsRepository.settings // e.g. Flow<Settings>
            .map { model ->
                mapOf(
                    "platform" to model.platform,
                    "player_type" to model.playerType,
                    "poll_interval" to model.pollInterval.toString()
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyMap()
            )

    // Online status from connectivity observer
    val isOnline: StateFlow<Boolean> =
        connectivityObserver.status // e.g. Flow<Boolean>
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = false
            )

    // Bot running flag
    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning.asStateFlow()

    init {
        // Optionally load recent players at startup if repository supports it
        viewModelScope.launch {
            playerRepository.recentPlayers().collect { list ->
                _recentPlayers.value = list
            }
        }
    }

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
            id = System.currentTimeMillis(), // Long
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
        if (!_isRunning.value) {
            _isRunning.value = true
            addLog("Bot started")
        }
    }

    fun stopBot() {
        if (_isRunning.value) {
            _isRunning.value = false
            addLog("Bot stopped")
        }
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