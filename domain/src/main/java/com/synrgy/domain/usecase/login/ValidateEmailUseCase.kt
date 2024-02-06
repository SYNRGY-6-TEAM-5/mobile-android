package com.synrgy.domain.usecase.login

interface ValidateEmailUseCase {
    suspend fun invoke(
        email: String
    ): Boolean
}