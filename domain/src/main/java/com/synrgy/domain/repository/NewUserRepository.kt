package com.synrgy.domain.repository

import kotlinx.coroutines.flow.Flow

interface NewUserRepository {
    suspend fun getIsNewUser(): Flow<Boolean?>
    suspend fun setIsNewUser(isNewUser: Boolean)
}