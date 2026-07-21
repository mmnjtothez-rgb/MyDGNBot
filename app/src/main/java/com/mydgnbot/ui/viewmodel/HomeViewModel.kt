package com.mydgnbot.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mydgnbot.data.mapper.ApiPlayerMapper.toPlayer
import com.mydgnbot.data.network.ConnectivityObserver
import com.mydgnbot.data.repository.PlayerRepository
import com.mydgnbot.data.repository.SettingsRepository
import com.mydgnbot.domain.model.Player
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class HomeViewModel(

    private val playerRepository: PlayerRepository,

    private val settingsRepository: SettingsRepository,

    connectivityObserver: ConnectivityObserver

) : ViewModel() {


    private val _player =
        MutableStateFlow<Player?>(null)

    val player: StateFlow<Player?> =
        _player



    private val _status =
        MutableStateFlow("Ready")

    val status: StateFlow<String> =
        _status



    private val _isRunning =
        MutableStateFlow(false)

    val isRunning: StateFlow<Boolean> =
        _isRunning



    private var searchJob: Job? = null



    val settings =
        settingsRepository.settings
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyMap()
            )



    val isOnline =
        connectivityObserver.isOnline
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = false
            )



    fun startBot() {

        if (_isRunning.value) return

        _isRunning.value = true

        searchJob = viewModelScope.launch {

            while (isActive && _isRunning.value) {

                fetchPlayer()

                if (_player.value != null) {

                    stopBot()

                    break

                }

                val currentSettings =
                    settings.first()

                val intervalSeconds =
                    currentSettings["poll_interval"]
                        ?.toLongOrNull()
                        ?: 10L

                _status.value =
                    "Waiting..."

                delay(intervalSeconds * 1000)

            }

        }

    }



    fun stopBot() {

        searchJob?.cancel()

        searchJob = null

        _isRunning.value = false

        _status.value = "Ready"

    }



    suspend fun fetchPlayer() {

        _status.value = "Searching..."

        val currentSettings =
            settings.first()

        val result =
            playerRepository.fetchPlayers(

                user =
                    currentSettings["api_user"]
                        ?: "",

                secretKey =
                    currentSettings["secret_key"]
                        ?: "",

                platform =
                    currentSettings["platform"]
                        ?: "Console",

                playerType =
                    currentSettings["player_type"]
                        ?.toIntOrNull()
                        ?: 2,

                minimumPrice =
                    currentSettings["minimum_price"]
                        ?.toIntOrNull()
                        ?: 4000,

                maximumPrice =
                    currentSettings["maximum_price"]
                        ?.toIntOrNull()
                        ?: 300000

            )

        val apiPlayer =
            result.firstOrNull()

        if (apiPlayer != null) {

            _player.value =
                apiPlayer.toPlayer()

            _status.value =
                "Player found"

        } else {

            _player.value = null

            _status.value =
                "No player found"

        }

    }

}
