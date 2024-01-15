package com.synrgy.domain.usecase.login

interface LoginValidateInputUseCase {
    suspend fun invoke(
        email: String,
        password: String,
    ): Boolean
}