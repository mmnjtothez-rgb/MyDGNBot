package com.mydgnbot.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mydgnbot.data.network.ConnectivityObserver
import com.mydgnbot.data.repository.PlayerEnrichmentRepository
import com.mydgnbot.data.repository.PlayerRepository
import com.mydgnbot.data.repository.SettingsRepository
import com.mydgnbot.domain.model.LogEntry
import com.mydgnbot.domain.model.Player
import com.mydgnbot.ui.components.BotStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
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

    // Current player shown in UI
    private val _player = MutableStateFlow<Player?>(null)
    val player: StateFlow<Player?> = _player.asStateFlow()

    // Recent players for History
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
            HistoryFilter.FOUND ->
                players.filter { it.status.contains("found", ignoreCase = true) }
            HistoryFilter.BOUGHT ->
                players.filter { it.status.contains("bought", ignoreCase = true) }
            HistoryFilter.CANCELLED ->
                players.filter { it.status.contains("cancel", ignoreCase = true) }
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

    // History panel visibility
    private val _showHistory = MutableStateFlow(false)
    val showHistory: StateFlow<Boolean> = _showHistory.asStateFlow()

    // Logs (if you still show them elsewhere)
    private val _logs = MutableStateFlow<List<LogEntry>>(emptyList())
    val logs: StateFlow<List<LogEntry>> = _logs.asStateFlow()

    private val stampFormat = DateTimeFormatter.ofPattern("HH:mm:ss")

    // Settings map from DataStore/Repository
    val settings: StateFlow<Map<String, String>> =
        settingsRepository.settings
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyMap()
            )

    // Connectivity state
    val isOnline: StateFlow<Boolean> =
        connectivityObserver.isOnline
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = false
            )

    // Bot running flag
    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning.asStateFlow()

    // Compact status strip data
    private val _botStatus = MutableStateFlow(BotStatus.WAITING)
    val botStatus: StateFlow<BotStatus> = _botStatus.asStateFlow()

    private val _waitSeconds = MutableStateFlow(0)
    val waitSeconds: StateFlow<Int> = _waitSeconds.asStateFlow()

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
            id = System.currentTimeMillis(),
            message = message,
            timestamp = LocalDateTime.now().format(stampFormat)
        )
        _logs.update { current ->
            val updated = current + entry
            if (updated.size > 50) updated.takeLast(50) else updated
        }
    }

    fun onPlayerFound(found: Player) {
        _player.value = found
        addRecentPlayer(found)
        _botStatus.value = BotStatus.PLAYER_FOUND
        addLog("Player found (${found.playerName})")
    }

    fun startBot() {
        if (_isRunning.value) return

        _isRunning.value = true
        _botStatus.value = BotStatus.SEARCHING
        addLog("Bot started")

        viewModelScope.launch {
            while (_isRunning.value) {
                if (!isOnline.value) {
                    _botStatus.value = BotStatus.WAITING
                    _waitSeconds.value = 2
                    addLog("Waiting for connection...")
                    delay(2_000)
                    continue
                }

                val currentSettings = settings.value
                val apiUser = currentSettings["api_user"].orEmpty()
                val secretKey = currentSettings["secret_key"].orEmpty()
                val platform = currentSettings["platform"] ?: "Console"
                val minPrice = currentSettings["minimum_price"]?.toIntOrNull() ?: 4000
                val maxPrice = currentSettings["maximum_price"]?.toIntOrNull() ?: 300000
                val playerType = currentSettings["player_type"]?.toIntOrNull() ?: 2
                val pollSeconds = currentSettings["poll_interval"]?.toLongOrNull() ?: 10L

                if (apiUser.isBlank() || secretKey.isBlank()) {
                    _botStatus.value = BotStatus.WAITING
                    _waitSeconds.value = pollSeconds.toInt()
                    addLog("Missing API credentials")
                    delay(pollSeconds * 1000)
                    continue
                }

                try {
                    _botStatus.value = BotStatus.SEARCHING
                    _waitSeconds.value = 0

                    val apiPlayers = playerRepository.fetchPlayers(
                        user = apiUser,
                        secretKey = secretKey,
                        platform = platform,
                        playerType = playerType,
                        minimumPrice = minPrice,
                        maximumPrice = maxPrice
                    )

                    if (apiPlayers.isNotEmpty()) {
                        val apiPlayer = apiPlayers.first()
                        val domainPlayer = playerEnrichmentRepository.enrich(apiPlayer)
                        onPlayerFound(domainPlayer)
                    } else {
                        _botStatus.value = BotStatus.NO_PLAYER
                        addLog("No players found")
                    }
                } catch (e: Exception) {
                    _botStatus.value = BotStatus.WAITING
                    addLog("Error fetching players: ${e.message ?: "unknown error"}")
                }

                _botStatus.value = BotStatus.WAITING
                _waitSeconds.value = pollSeconds.toInt()
                delay(pollSeconds * 1000)
            }
        }
    }

    fun stopBot() {
        if (!_isRunning.value) return
        _isRunning.value = false
        _botStatus.value = BotStatus.WAITING
        _waitSeconds.value = 0
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