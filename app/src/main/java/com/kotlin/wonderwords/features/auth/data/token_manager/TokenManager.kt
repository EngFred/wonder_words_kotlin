package com.kotlin.wonderwords.features.auth.data.token_manager

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TokenManager @Inject constructor(
    private val datastore: DataStore<Preferences>
) {

    companion object {
        val USER_TOKEN = stringPreferencesKey("USER_TOKEN")
        val USER_NAME = stringPreferencesKey("USER_NAME")
        const val TAG = "TokenManager"
    }

    @Volatile
    private var cachedToken: String? = null

    suspend fun saveUserInfo( userToken: String, userName: String ) {
        try {
            datastore.edit { pref ->
                pref[USER_TOKEN] = userToken
                pref[USER_NAME] = userName
            }
            cachedToken = userToken
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
        }
    }

    val getUserToken : Flow<String> = datastore.data.map {  pref ->
        pref[USER_TOKEN] ?: ""
    }.distinctUntilChanged().catch { throwable ->
        Log.d(TAG, throwable.message.toString())
    }

    val getUserName : Flow<String> = datastore.data.map {  pref ->
        pref[USER_NAME] ?: ""
    }.distinctUntilChanged().catch { throwable ->
        Log.d(TAG, throwable.message.toString())
    }

    suspend fun clearUserInfo() {
        datastore.edit { preferences ->
            preferences.remove(USER_TOKEN)
            preferences.remove(USER_NAME)
        }
        cachedToken = null
    }

    suspend fun getToken(): String? {
        if (cachedToken == null) {
            cachedToken = getUserToken.firstOrNull()
        }
        return cachedToken
    }
}