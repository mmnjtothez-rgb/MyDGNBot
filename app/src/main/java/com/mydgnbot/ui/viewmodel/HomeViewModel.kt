package com.mydgnbot.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mydgnbot.data.mapper.ApiPlayerMapper.toPlayer
import com.mydgnbot.data.network.ConnectivityObserver
import com.mydgnbot.data.repository.PlayerRepository
import com.mydgnbot.data.repository.SettingsRepository
import com.mydgnbot.domain.model.LogEntry
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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



private val _logs =  
    MutableStateFlow<List<LogEntry>>(emptyList())  

val logs: StateFlow<List<LogEntry>> =  
    _logs  



private var searchJob: Job? = null

private var logIdCounter = 0L

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



private fun addLog(  
    message: String  
) {  

    val time =  
        SimpleDateFormat(  
            "HH:mm:ss",  
            Locale.getDefault()  
        ).format(Date())  


    val entry =  
        LogEntry(  

            id =  
++logIdCounter,  

            message =  
                message,  

            timestamp =  
                time  

        )  


    _logs.value =  
(_logs.value + entry).takeLast(20)  

}  



fun startBot() {  

    if (_isRunning.value) return  


    _isRunning.value = true  

    addLog(  
        "Bot started"  
    )  


    searchJob =  
        viewModelScope.launch {  


            while (  
                isActive &&  
                _isRunning.value  
            ) {  


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


                addLog(  
                    "Waiting ${intervalSeconds}s"  
                )  


                delay(  
                    intervalSeconds * 1000  
                )  

            }  

        }  

}  



fun stopBot() {  

    searchJob?.cancel()  

    searchJob = null  

    _isRunning.value = false  

    _status.value =  
        "Ready"  


    addLog(  
        "Bot stopped"  
    )  

}  



suspend fun fetchPlayer() {  

    _status.value =  
        "Searching..."  


    addLog(  
        "Searching..."  
    )  


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


        addLog(  
            "Player found"  
        )  


    } else {  

        _player.value = null  


        _status.value =  
            "No player found"  


        addLog(  
            "No player found"  
        )  

    }  

}  



fun markBought() {  

    viewModelScope.launch {  


        val currentPlayer =  
            _player.value  
                ?: return@launch  


        val transactionId =  
            currentPlayer.transactionId  
                .toIntOrNull()  
                ?: return@launch  


        val currentSettings =  
            settings.first()  



        _status.value =  
            "Sending bought..."  


        addLog(  
            "Sending bought"  
        )  



        val response =  
            playerRepository.updateOrderStatus(  

                user =  
                    currentSettings["api_user"]  
                        ?: "",  

                secretKey =  
                    currentSettings["secret_key"]  
                        ?: "",  

                platform =  
                    currentSettings["platform"]  
                        ?: "Console",  

                transactionId =  
                    transactionId,  

                status =  
                    "bought",  

                eaEmail =  
                    currentSettings["ea_email"]  
                        ?: ""  

            )  



        if (response?.code == 200) {  


            _status.value =  
                "Bought ✓"  


            addLog(  
                "Bought success"  
            )  


            _player.value = null  


            delay(1000)  


            _status.value =  
                "Ready"  


            startBot()  


        } else {  


            _status.value =  
                response?.status  
                    ?: "Bought failed"  


            addLog(  
                "Bought failed"  
            )  

        }  

    }  

}  



fun cancelPlayer() {  

    viewModelScope.launch {  


        val currentPlayer =  
            _player.value  
                ?: return@launch  


        val transactionId =  
            currentPlayer.transactionId  
                .toIntOrNull()  
                ?: return@launch  


        val currentSettings =  
            settings.first()  



        _status.value =  
            "Sending cancel..."  


        addLog(  
            "Sending cancel"  
        )  



        val response =  
            playerRepository.updateOrderStatus(  

                user =  
                    currentSettings["api_user"]  
                        ?: "",  

                secretKey =  
                    currentSettings["secret_key"]  
                        ?: "",  

                platform =  
                    currentSettings["platform"]  
                        ?: "Console",  

                transactionId =  
                    transactionId,  

                status =  
                    "cancel",  

                code =  
                    551  

            )  



        if (response?.code == 200) {  


            _status.value =  
                "Cancelled ✓"  


            addLog(  
                "Cancel success"  
            )  


            _player.value = null  


            delay(1000)  


            _status.value =  
                "Ready"  


            startBot()  


        } else {  


            _status.value =  
                response?.status  
                    ?: "Cancel failed"  


            addLog(  
                "Cancel failed"  
            )  

        }  

    }  

}

}
