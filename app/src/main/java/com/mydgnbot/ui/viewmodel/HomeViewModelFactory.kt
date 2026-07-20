package com.mydgnbot.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mydgnbot.data.repository.PlayerRepository
import com.mydgnbot.data.repository.SettingsRepository


class HomeViewModelFactory(
    private val playerRepository: PlayerRepository,
    private val settingsRepository: SettingsRepository
) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {


        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {


            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(
                playerRepository,
                settingsRepository
            ) as T


        }


        throw IllegalArgumentException(
            "Unknown ViewModel class"
        )

    }

}
