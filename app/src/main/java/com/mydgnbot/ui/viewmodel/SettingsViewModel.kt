package com.mydgnbot.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mydgnbot.data.repository.SettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val repository: SettingsRepository
) : ViewModel() {


    val settings =
        repository.settings
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyMap()
            )


    fun saveSettings(

        apiUser: String,

        secretKey: String,

        eaEmail: String,

        platform: String,

        minimumPrice: String,

        maximumPrice: String,

        playerType: String,

        pollInterval: String

    ) {

        viewModelScope.launch {

            repository.saveSettings(

                apiUser = apiUser,

                secretKey = secretKey,

                eaEmail = eaEmail,

                platform = platform,

                minimumPrice = minimumPrice,

                maximumPrice = maximumPrice,

                playerType = playerType,

                pollInterval = pollInterval

            )

        }

    }

}
