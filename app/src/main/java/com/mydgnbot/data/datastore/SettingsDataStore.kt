package com.mydgnbot.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(
    name = "settings"
)

class SettingsDataStore(
    private val context: Context
) {

    private object Keys {

        val API_USER =
            stringPreferencesKey("api_user")

        val SECRET_KEY =
            stringPreferencesKey("secret_key")

        val PLATFORM =
            stringPreferencesKey("platform")
    }


    val settings: Flow<Map<String, String>> =

        context.dataStore.data.map { preferences ->

            mapOf(

                "api_user" to (
                    preferences[Keys.API_USER] ?: ""
                ),

                "secret_key" to (
                    preferences[Keys.SECRET_KEY] ?: ""
                ),

                "platform" to (
                    preferences[Keys.PLATFORM] ?: "Console"
                )

            )

        }


    suspend fun saveSettings(
        apiUser: String,
        secretKey: String,
        platform: String
    ) {

        context.dataStore.edit { preferences ->

            preferences[Keys.API_USER] = apiUser

            preferences[Keys.SECRET_KEY] = secretKey

            preferences[Keys.PLATFORM] = platform

        }
    }
}
