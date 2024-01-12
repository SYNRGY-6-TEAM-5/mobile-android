package com.synrgy.data.local

import com.synrgy.domain.repository.AuthRepository
import com.synrgy.domain.repository.LoginRepository
import com.synrgy.domain.repository.NewUserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalRepository @Inject constructor (
    private val dataStoreManager: DataStoreManager
): LoginRepository, AuthRepository, NewUserRepository {
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
}