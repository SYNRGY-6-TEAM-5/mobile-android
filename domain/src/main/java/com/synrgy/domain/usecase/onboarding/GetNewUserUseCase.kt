package com.synrgy.domain.usecase.onboarding

import kotlinx.coroutines.flow.Flow

interface GetNewUserUseCase {
    suspend fun invoke(): Flow<Boolean?>
}