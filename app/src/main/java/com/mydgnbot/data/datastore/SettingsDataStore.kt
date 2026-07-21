package com.mydgnbot.data.datastore

import android.content.Context
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

        val EA_EMAIL =
            stringPreferencesKey("ea_email")

        val PLATFORM =
            stringPreferencesKey("platform")

        val MINIMUM_PRICE =
            stringPreferencesKey("minimum_price")

        val MAXIMUM_PRICE =
            stringPreferencesKey("maximum_price")

        val PLAYER_TYPE =
            stringPreferencesKey("player_type")

        val POLL_INTERVAL =
            stringPreferencesKey("poll_interval")

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

                "ea_email" to (
                    preferences[Keys.EA_EMAIL] ?: ""
                ),

                "platform" to (
                    preferences[Keys.PLATFORM] ?: "Console"
                ),

                "minimum_price" to (
                    preferences[Keys.MINIMUM_PRICE] ?: "4000"
                ),

                "maximum_price" to (
                    preferences[Keys.MAXIMUM_PRICE] ?: "300000"
                ),

                "player_type" to (
                    preferences[Keys.PLAYER_TYPE] ?: "2"
                ),

                "poll_interval" to (
                    preferences[Keys.POLL_INTERVAL] ?: "10"
                )

            )

        }


    suspend fun saveSettings(

        apiUser: String,

        secretKey: String,

        eaEmail: String,

        platform: String,

        minimumPrice: String,

        maximumPrice: String,

        playerType: String,

        pollInterval: String

    ) {

        context.dataStore.edit { preferences ->

            preferences[Keys.API_USER] =
                apiUser

            preferences[Keys.SECRET_KEY] =
                secretKey

            preferences[Keys.EA_EMAIL] =
                eaEmail

            preferences[Keys.PLATFORM] =
                platform

            preferences[Keys.MINIMUM_PRICE] =
                minimumPrice

            preferences[Keys.MAXIMUM_PRICE] =
                maximumPrice

            preferences[Keys.PLAYER_TYPE] =
                playerType

            preferences[Keys.POLL_INTERVAL] =
                pollInterval

        }

    }

}
