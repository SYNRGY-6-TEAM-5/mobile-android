package com.synrgy.domain.usecase.auth

import kotlinx.coroutines.flow.Flow

interface GetUserIdUseCase {
    suspend fun invoke(): Flow<String?>
}