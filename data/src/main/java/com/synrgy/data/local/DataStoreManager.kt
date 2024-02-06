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
        private val KEY_PHOTO = stringPreferencesKey("photo")
        private val KEY_IS_NEW_USER = booleanPreferencesKey("is_new_user")
        private val KEY_REG_TOKEN = stringPreferencesKey("reg_token")
        private val KEY_USER_ID = stringPreferencesKey("user_id")

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
                preferences.remove(KEY_PHOTO)
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

    suspend fun setPhoto(photoUrl: String) {
        context.dataStore.edit { preferences -> preferences[KEY_PHOTO] = photoUrl }
    }

    fun getPhoto(): Flow<String?> {
        return context.dataStore.data.map { preferences -> preferences[KEY_PHOTO] }
    }

    suspend fun setRegToken(token: String) {
        context.dataStore.edit { preferences -> preferences[KEY_REG_TOKEN] = token }
    }

    fun getRegToken(): Flow<String?> {
        return context.dataStore.data.map { preferences -> preferences[KEY_REG_TOKEN] }
    }

    suspend fun setUserId(id: String) {
        context.dataStore.edit { preferences -> preferences[KEY_USER_ID] = id }
    }

    fun getUserId(): Flow<String?> {
        return context.dataStore.data.map { preferences -> preferences[KEY_USER_ID] }
    }
}