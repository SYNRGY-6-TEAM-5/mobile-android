package com.synrgy.data.local

import android.util.Patterns
import com.synrgy.data.helper.Helper
import com.synrgy.domain.repository.AuthRepository
import com.synrgy.domain.repository.LoginRepository
import com.synrgy.domain.repository.NewUserRepository
import com.synrgy.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalRepository @Inject constructor (
    private val dataStoreManager: DataStoreManager
): LoginRepository, RegisterRepository, AuthRepository, NewUserRepository {
    override suspend fun validateLogin(email: String, password: String): Boolean {
        return email.isNotEmpty()
                && email.isNotBlank()
                && password.isNotEmpty()
                && password.isNotBlank()
    }

    override suspend fun validateEmail(email: String): Boolean {
        return email.isNotEmpty()
                && email.isNotBlank()
    }

    override suspend fun validateChangePass(newPassword: String, retypePassword: String): Boolean {
        return newPassword.isNotEmpty()
                && newPassword.isNotBlank()
                && Helper.containsSpecialCharacter(newPassword)
                && Helper.containsAlphanumeric(newPassword)
                && Helper.containsUppercaseLetter(newPassword)
                && retypePassword.isNotEmpty()
                && retypePassword.isNotBlank()
                && newPassword == retypePassword
    }

    override suspend fun validateRegister(email: String, password: String): Boolean {
        return email.isNotEmpty()
                && email.isNotBlank()
                && Patterns.EMAIL_ADDRESS.matcher(email).matches()
                && password.isNotEmpty()
                && password.isNotBlank()
                && Helper.checkPasswordLength(password)
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

    override suspend fun getUserId(): Flow<String?> {
        return dataStoreManager.getUserId()
    }

    override suspend fun setUserId(id: String) {
        dataStoreManager.setUserId(id)
    }
}