package com.synrgy.domain.usecase.login

import kotlinx.coroutines.flow.Flow

interface GetTokenUseCase {
    suspend fun invoke(): Flow<String?>
}