package com.synrgy.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun clearToken()
    suspend fun getName(): Flow<String?>
    suspend fun setName(name: String)
}