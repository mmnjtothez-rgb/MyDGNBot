package com.mydgnbot.ui.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mydgnbot.data.mapper.ApiPlayerMapper.toPlayer
import com.mydgnbot.data.repository.PlayerRepository
import com.mydgnbot.data.repository.SettingsRepository
import com.mydgnbot.domain.model.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class HomeViewModel(
    private val playerRepository: PlayerRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {


    private val _player =
        MutableStateFlow<Player?>(null)


    val player: StateFlow<Player?> =
        _player



    private val _status =
        MutableStateFlow("Ready")


    val status: StateFlow<String> =
        _status



    fun fetchPlayer() {


        viewModelScope.launch {


            _status.value =
                "Searching..."


            val settings =
                settingsRepository.settings.first()



            val result =

                playerRepository.fetchPlayers(

                    user =
                        settings["api_user"]
                            ?: "",


                    secretKey =
                        settings["secret_key"]
                            ?: "",


                    platform =
                        settings["platform"]
                            ?: "Console",


                    playerType =
                        settings["player_type"]
                            ?.toIntOrNull()
                            ?: 2,


                    minimumPrice =
                        settings["minimum_price"]
                            ?.toIntOrNull()
                            ?: 4000,


                    maximumPrice =
                        settings["maximum_price"]
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
