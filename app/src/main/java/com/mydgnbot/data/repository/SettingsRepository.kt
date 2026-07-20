package com.mydgnbot.data.repository

import com.mydgnbot.data.datastore.SettingsDataStore
import kotlinx.coroutines.flow.Flow

class SettingsRepository(
    private val dataStore: SettingsDataStore
) {

    val settings: Flow<Map<String, String>> =
        dataStore.settings


    suspend fun saveSettings(
        apiUser: String,
        secretKey: String,
        platform: String,
        minimumPrice: String,
        maximumPrice: String,
        playerType: String,
        pollInterval: String
    ) {

        dataStore.saveSettings(
            apiUser = apiUser,
            secretKey = secretKey,
            platform = platform,
            minimumPrice = minimumPrice,
            maximumPrice = maximumPrice,
            playerType = playerType,
            pollInterval = pollInterval
        )

    }
}
