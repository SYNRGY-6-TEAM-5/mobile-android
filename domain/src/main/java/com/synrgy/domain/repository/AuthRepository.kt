package com.synrgy.domain.repository

interface AuthRepository {
    suspend fun clearToken()
}