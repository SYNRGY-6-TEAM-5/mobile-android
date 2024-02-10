package com.synrgy.presentation.usecase.auth

import com.synrgy.domain.repository.AuthRepository
import com.synrgy.domain.usecase.auth.SetPhotoUseCase
import javax.inject.Inject

class SetPhotoUseCase @Inject constructor(
    private val authRepository: AuthRepository
): SetPhotoUseCase {
    override suspend operator fun invoke(photoUrl: String) {
        authRepository.setPhoto(photoUrl)
    }
}