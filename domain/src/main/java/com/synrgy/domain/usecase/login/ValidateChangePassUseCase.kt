package com.synrgy.domain.usecase.login

interface ValidateChangePassUseCase {
    suspend fun invoke(
        newPassword: String,
        retypePassword: String
    ): Boolean
}