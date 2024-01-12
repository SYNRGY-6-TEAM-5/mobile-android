package com.synrgy.domain.usecase.auth

interface SetPhotoUseCase {
    suspend fun invoke(
        photoUrl: String
    )
}