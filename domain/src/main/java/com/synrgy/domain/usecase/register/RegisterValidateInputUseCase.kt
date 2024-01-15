package com.synrgy.domain.usecase.register

interface RegisterValidateInputUseCase {
    suspend fun invoke(
        email: String,
        password: String,
    ): Boolean
}