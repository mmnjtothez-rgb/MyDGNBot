package com.mydgnbot.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mydgnbot.data.repository.PlayerRepository
import com.mydgnbot.data.mapper.toPlayer
import com.mydgnbot.domain.model.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class HomeViewModel(
    private val repository: PlayerRepository
) : ViewModel() {


    private val _player =
        MutableStateFlow<Player?>(null)


    val player: StateFlow<Player?> =
        _player



    private val _status =
        MutableStateFlow("Ready")


    val status: StateFlow<String> =
        _status



    fun fetchPlayer(

        user: String,

        secretKey: String,

        platform: String,

        playerType: Int,

        minimumPrice: Int,

        maximumPrice: Int

    ) {


        viewModelScope.launch {


            _status.value =
                "Searching..."



            val result =

                repository.fetchPlayers(

                    user = user,

                    secretKey = secretKey,

                    platform = platform,

                    playerType = playerType,

                    minimumPrice = minimumPrice,

                    maximumPrice = maximumPrice

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
