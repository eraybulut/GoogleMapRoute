package com.eraybulut.mapexample.data

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Created by Eray BULUT on 23.12.2023
 * eraybulutlar@gmail.com
 */

class PrefDataSource @Inject constructor(@ApplicationContext context: Context) {

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val sharedPref = EncryptedSharedPreferences.create(
        PREF_NAME,
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun <T> saveData(data: T) {
        Gson().toJson(data).also {
            sharedPref.edit().putString(SAVE_ADDRESS_KEY, it).apply()
        }
    }

    fun <T> getData(clazz: Class<T>): T? {
        val jsonData = sharedPref.getString(SAVE_ADDRESS_KEY, null)
        return if (jsonData != null) {
            val gson = Gson()
            gson.fromJson(jsonData, clazz)
        } else {
            null
        }
    }


    fun clear() {
        sharedPref.edit().clear().apply()
    }

    companion object {
        private const val PREF_NAME = "encrypted_prefs"
        private const val SAVE_ADDRESS_KEY = "SAVE_ADDRESS_KEY"
    }
}