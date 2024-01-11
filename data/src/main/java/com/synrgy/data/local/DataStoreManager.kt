package com.synrgy.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
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
        private val KEY_NAME = stringPreferencesKey("name")
        private val KEY_IS_NEW_USER = booleanPreferencesKey("is_new_user")
        private const val PREF_NAME = "myPreferences"

        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREF_NAME)
    }

    suspend fun setToken(token: String) {
        context.dataStore.edit { preferences -> preferences[KEY_TOKEN] = token }
    }

    suspend fun clearToken() {
        context.dataStore.edit { preferences ->
            run {
                preferences.remove(KEY_TOKEN)
                preferences.remove(KEY_NAME)
            }
        }
    }

    fun getToken(): Flow<String?> {
        return context.dataStore.data.map { preferences -> preferences[KEY_TOKEN] }
    }

    fun getIsNewUser(): Flow<Boolean?> {
        return context.dataStore.data.map { preferences -> preferences[KEY_IS_NEW_USER] }
    }

    suspend fun setIsNewUser(isNewUser: Boolean) {
        context.dataStore.edit { preferences -> preferences[KEY_IS_NEW_USER] = isNewUser }
    }

    suspend fun setName(name: String) {
        context.dataStore.edit { preferences -> preferences[KEY_NAME] = name }
    }

    fun getName(): Flow<String?> {
        return context.dataStore.data.map { preferences -> preferences[KEY_NAME] }
    }
}