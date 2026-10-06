package com.minicodex.core.security

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

/**
 * Handles secure storage of API keys and sensitive settings.
 */
class SecureStorage(context: Context) {
    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val sharedPreferences = EncryptedSharedPreferences.create(
        "minicodex_secure_prefs",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveApiKey(provider: String, key: String) {
        sharedPreferences.edit().putString("api_key_$provider", key).apply()
    }

    fun getApiKey(provider: String): String? {
        return sharedPreferences.getString("api_key_$provider", null)
    }

    fun clearApiKey(provider: String) {
        sharedPreferences.edit().remove("api_key_$provider").apply()
    }
}
