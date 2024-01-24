package com.synrgy.data.local

import android.util.Patterns
import com.synrgy.data.helper.Helper
import com.synrgy.domain.repository.AuthRepository
import com.synrgy.domain.repository.LoginRepository
import com.synrgy.domain.repository.NewUserRepository
import com.synrgy.domain.repository.RegisterRepository
import com.synrgy.domain.response.airport.AirportData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalRepository @Inject constructor (
    private val dataStoreManager: DataStoreManager,
    private val sharedPreferences: SharedPreferences
): LoginRepository, RegisterRepository, AuthRepository, NewUserRepository {
    override suspend fun validateInput(email: String, password: String): Boolean {
        return email.isNotEmpty()
                && email.isNotBlank()
                && Patterns.EMAIL_ADDRESS.matcher(email).matches()
                && password.isNotEmpty()
                && password.isNotBlank()
                && Helper.containsSpecialCharacter(password)
                && Helper.containsAlphanumeric(password)
                && Helper.containsUppercaseLetter(password)
    }

    override suspend fun setToken(token: String) {
        dataStoreManager.setToken(token)
    }

    override suspend fun getToken(): Flow<String?> {
        return dataStoreManager.getToken()
    }

    override suspend fun clearToken() {
        dataStoreManager.clearToken()
    }

    override suspend fun getIsNewUser(): Flow<Boolean?> {
        return dataStoreManager.getIsNewUser()
    }

    override suspend fun setIsNewUser(isNewUser: Boolean) {
        dataStoreManager.setIsNewUser(isNewUser)
    }

    override suspend fun setName(name: String) {
        dataStoreManager.setName(name)
    }

    override suspend fun getName(): Flow<String?> {
        return dataStoreManager.getName()
    }

    override suspend fun setPhoto(photoUrl: String) {
        dataStoreManager.setPhoto(photoUrl)
    }

    override suspend fun getPhoto(): Flow<String?> {
        return dataStoreManager.getPhoto()
    }

    override suspend fun getRegToken(): Flow<String?> {
        return dataStoreManager.getRegToken()
    }

    override suspend fun setRegToken(token: String) {
        dataStoreManager.setRegToken(token)
    }

    override suspend fun setRecentAirport(data: AirportData) {
        sharedPreferences.setRecentAirport(data)
    }

    override suspend fun getRecentAirport(): MutableSet<String>? {
        return sharedPreferences.getRecentAirport()
    }
}