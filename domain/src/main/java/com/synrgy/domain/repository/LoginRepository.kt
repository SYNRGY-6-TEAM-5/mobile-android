package com.synrgy.domain.repository

import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun validateInput(username: String, password: String): Boolean
    suspend fun getToken(): Flow<String?>
    suspend fun setToken(token: String)
}