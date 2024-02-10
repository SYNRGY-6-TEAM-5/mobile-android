package com.synrgy.domain.usecase.auth

import kotlinx.coroutines.flow.Flow

interface GetRegTokenUseCase {
    suspend fun invoke(): Flow<String?>
}