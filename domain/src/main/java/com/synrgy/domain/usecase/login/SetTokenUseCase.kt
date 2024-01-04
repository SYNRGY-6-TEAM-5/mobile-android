package com.synrgy.domain.usecase.login

interface SetTokenUseCase {
    suspend fun invoke(
        token: String
    )
}