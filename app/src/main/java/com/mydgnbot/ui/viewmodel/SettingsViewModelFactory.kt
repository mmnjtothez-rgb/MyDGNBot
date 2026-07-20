package com.mydgnbot.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mydgnbot.data.repository.SettingsRepository

class SettingsViewModelFactory(
    private val repository: SettingsRepository
) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {

            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(
                repository
            ) as T

        }

        throw IllegalArgumentException(
            "Unknown ViewModel class"
        )
    }
}
