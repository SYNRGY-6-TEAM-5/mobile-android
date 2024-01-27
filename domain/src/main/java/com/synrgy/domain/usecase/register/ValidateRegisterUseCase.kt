package com.synrgy.domain.usecase.register

interface ValidateRegisterUseCase {
    suspend fun invoke(
        email: String,
        password: String,
    ): Boolean
}