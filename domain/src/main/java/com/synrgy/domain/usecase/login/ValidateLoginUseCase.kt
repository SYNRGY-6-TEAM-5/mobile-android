package com.synrgy.domain.usecase.login

interface ValidateLoginUseCase {
    suspend fun invoke(
        email: String,
        password: String,
    ): Boolean
}