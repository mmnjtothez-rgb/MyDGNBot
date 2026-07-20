package com.mydgnbot.data

import android.content.Context
import com.mydgnbot.data.datastore.SettingsDataStore
import com.mydgnbot.data.repository.PlayerRepository
import com.mydgnbot.data.repository.SettingsRepository

class AppContainer(
    context: Context
) {

    private val settingsDataStore =
        SettingsDataStore(context)

    val settingsRepository =
        SettingsRepository(
            settingsDataStore
        )

    val playerRepository =
        PlayerRepository()

}
