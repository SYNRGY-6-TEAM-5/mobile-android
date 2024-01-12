package com.synrgy.domain.usecase.auth

import kotlinx.coroutines.flow.Flow

interface GetNameUseCase {
    suspend fun invoke(): Flow<String?>
}