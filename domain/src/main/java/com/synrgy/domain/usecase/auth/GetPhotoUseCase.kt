package com.synrgy.domain.usecase.auth

import kotlinx.coroutines.flow.Flow

interface GetPhotoUseCase {
    suspend fun invoke(): Flow<String?>
}