package com.mydgnbot.ui.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mydgnbot.data.mapper.ApiPlayerMapper.toPlayer
import com.mydgnbot.data.network.ConnectivityObserver
import com.mydgnbot.data.repository.PlayerRepository
import com.mydgnbot.data.repository.SettingsRepository
import com.mydgnbot.domain.model.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
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





    val settings =

        settingsRepository.settings

            .stateIn(

                scope = viewModelScope,

                started = SharingStarted.WhileSubscribed(5000),

                initialValue = emptyMap()

            )





    val isOnline: StateFlow<Boolean> =

        connectivityObserver.isOnline

            .stateIn(

                scope = viewModelScope,

                started = SharingStarted.WhileSubscribed(5000),

                initialValue = false

            )





    fun fetchPlayer() {


        viewModelScope.launch {


            _status.value =

                "Searching..."



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


                _status.value =

                    "No player found"

            }


        }

    }


}
