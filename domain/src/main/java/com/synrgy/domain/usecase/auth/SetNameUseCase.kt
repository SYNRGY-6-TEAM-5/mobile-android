package com.synrgy.domain.usecase.auth

interface SetNameUseCase {
    suspend fun invoke(
        name: String
    )
}