package com.synrgy.presentation.usecase.auth

import com.synrgy.domain.repository.AuthRepository
import com.synrgy.domain.usecase.auth.SetRegTokenUseCase
import javax.inject.Inject

class SetRegTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
): SetRegTokenUseCase {
    override suspend operator fun invoke(token: String) {
        authRepository.setRegToken(token)
    }
}