package com.synrgy.domain.repository

import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun validateLogin(email: String, password: String): Boolean
    suspend fun validateEmail(email: String): Boolean
    suspend fun validateChangePass(newPassword: String, retypePassword: String): Boolean
    suspend fun getToken(): Flow<String?>
    suspend fun setToken(token: String)
}