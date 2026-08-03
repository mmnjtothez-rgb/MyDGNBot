package com.dsbot.app.data

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class CredentialManager(context: Context) {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val prefs = EncryptedSharedPreferences.create(
        context,
        "dsbot_secure_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SKEY_KEYGEN,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveCredentials(partnerId: String, secretKey: String) {
        prefs.edit()
            .putString("KEY_PARTNER_ID", partnerId.trim())
            .putString("KEY_SECRET_KEY", secretKey.trim())
            .apply()
    }

    fun getPartnerId(): String = prefs.getString("KEY_PARTNER_ID", "") ?: ""

    fun getSecretKey(): String = prefs.getString("KEY_SECRET_KEY", "") ?: ""

    fun hasCredentials(): Boolean {
        return getPartnerId().isNotBlank() && getSecretKey().isNotBlank()
    }
}
