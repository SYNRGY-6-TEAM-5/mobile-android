package com.synrgy.domain.usecase.auth

interface SetUserIdUseCase {
    suspend fun invoke(
        id: String
    )
}