package com.kotlin.wonderwords.core.presentation

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.kotlin.wonderwords.features.profile.domain.model.ThemeMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ThemeManager @Inject constructor(
    private val datastore: DataStore<Preferences>
) {

    companion object {
        val THEME = stringPreferencesKey("THEME")
        const val TAG = "ThemeManager"
    }

    suspend fun saveTheme( theme: ThemeMode ) {
        try {
            datastore.edit { pref ->
                pref[THEME] = theme.name.lowercase()
            }
            Log.d(TAG, "savedTheme: ${theme.name}")
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
        }
    }

    val getTheme : Flow<String> = datastore.data.map { pref ->
        pref[THEME] ?: ThemeMode.System.name.lowercase()
    }.distinctUntilChanged().catch { throwable ->
        Log.d(TAG, throwable.message.toString())
    }.flowOn(Dispatchers.IO)
}