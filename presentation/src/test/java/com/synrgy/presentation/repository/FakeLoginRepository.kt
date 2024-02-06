package com.synrgy.presentation.repository

import com.synrgy.domain.repository.LoginRepository
import com.synrgy.presentation.helper.Helper
import kotlinx.coroutines.flow.Flow

class FakeLoginRepository: LoginRepository {
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

    override suspend fun getToken(): Flow<String?> {
        TODO("Not yet implemented")
    }

    override suspend fun setToken(token: String) {
        TODO("Not yet implemented")
    }

}