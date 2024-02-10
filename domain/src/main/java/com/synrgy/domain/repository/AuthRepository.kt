package com.synrgy.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun clearToken()
    suspend fun getName(): Flow<String?>
    suspend fun setName(name: String)
    suspend fun getPhoto(): Flow<String?>
    suspend fun setPhoto(photoUrl: String)
    suspend fun getRegToken(): Flow<String?>
    suspend fun setRegToken(token: String)
    suspend fun getUserId(): Flow<String?>
    suspend fun setUserId(id: String)
}