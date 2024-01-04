package com.synrgy.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreManager @Inject constructor (
    private val context: Context
) {
    companion object {
        private val KEY_TOKEN = stringPreferencesKey("token")
        private const val PREF_NAME = "myPreferences"

        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREF_NAME)
    }

    suspend fun setToken(token: String) {
        context.dataStore.edit { preferences -> preferences[KEY_TOKEN] = token }
    }

    suspend fun clearToken() {
        context.dataStore.edit { preferences -> preferences.clear() }
    }

    fun getToken(): Flow<String?> {
        return context.dataStore.data.map { preferences -> preferences[KEY_TOKEN] }
    }
}