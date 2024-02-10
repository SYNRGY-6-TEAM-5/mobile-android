package com.synrgy.domain.usecase.auth

interface SetRegTokenUseCase {
    suspend fun invoke(
        token: String
    )
}