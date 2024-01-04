package com.synrgy.presentation.usecase.auth

import com.synrgy.domain.repository.AuthRepository
import com.synrgy.domain.usecase.auth.ClearTokenUseCase
import javax.inject.Inject

class ClearTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
): ClearTokenUseCase {
    override suspend operator fun invoke() {
        authRepository.clearToken()
    }
}