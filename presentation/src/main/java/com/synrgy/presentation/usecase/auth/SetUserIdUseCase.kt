package com.synrgy.presentation.usecase.auth

import com.synrgy.domain.repository.AuthRepository
import com.synrgy.domain.usecase.auth.SetUserIdUseCase
import javax.inject.Inject

class SetUserIdUseCase @Inject constructor(
    private val authRepository: AuthRepository
): SetUserIdUseCase {
    override suspend operator fun invoke(id: String) {
        authRepository.setUserId(id)
    }
}