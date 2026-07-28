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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HomeViewModel(
    private val playerRepository: PlayerRepository,
    private val settingsRepository: SettingsRepository,
    private val playerEnrichmentRepository: PlayerEnrichmentRepository,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {

    private val _player = MutableStateFlow<Player?>(null)
    val player: StateFlow<Player?> = _player.asStateFlow()

    private val _recentPlayers = MutableStateFlow<List<Player>>(emptyList())
    val recentPlayers: StateFlow<List<Player>> = _recentPlayers.asStateFlow()

    private val _showHistory = MutableStateFlow(false)
    val showHistory: StateFlow<Boolean> = _showHistory.asStateFlow()

    private val _logs = MutableStateFlow<List<LogEntry>>(emptyList())
    val logs: StateFlow<List<LogEntry>> = _logs.asStateFlow()

    private val timestampFormat = DateTimeFormatter.ofPattern("HH:mm:ss")

    fun requestHistory() {
        _showHistory.value = true
    }

    fun dismissHistory() {
        _showHistory.value = false
    }

    fun clearHistory() {
        _recentPlayers.value = emptyList()
        addLog("History cleared")
    }

    private fun addRecentPlayer(player: Player) {
        _recentPlayers.update { current ->
            buildList {
                add(player)
                current.forEach { existing ->
                    if (existing.name != player.name) add(existing)
                }
            }.take(20)
        }
    }

    private fun addLog(message: String) {
        val entry = LogEntry(
            message = message,
            timestamp = LocalDateTime.now().format(timestampFormat)
        )
        _logs.update { current -> (current + entry).takeLast(50) }
    }

    fun onPlayerFound(found: Player) {
        _player.value = found
        addRecentPlayer(found)
        addLog("Player found: ${found.name}")
    }

    fun startBot() {
        addLog("Bot started")
    }

    fun stopBot() {
        addLog("Bot stopped")
    }

    fun markBought() {
        _player.value?.let { found ->
            addLog("Bought: ${found.name}")
            addRecentPlayer(found)
        }
        _player.value = null
    }

    fun cancelPlayer() {
        _player.value?.let { found ->
            addLog("Cancelled: ${found.name}")
        }
        _player.value = null
    }
}