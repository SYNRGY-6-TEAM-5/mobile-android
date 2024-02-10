package com.synrgy.domain.usecase.onboarding

interface SetNewUserUseCase {
    suspend fun invoke(
        newUser: Boolean
    )
}