package com.synrgy.data.local

import com.synrgy.domain.repository.AuthRepository
import com.synrgy.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalRepository @Inject constructor (
    private val dataStoreManager: DataStoreManager
): LoginRepository, AuthRepository {
    override suspend fun validateInput(email: String, password: String): Boolean {
        return email.isNotEmpty()
                && email.isNotBlank()
                && password.isNotEmpty()
                && password.isNotBlank()
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
}