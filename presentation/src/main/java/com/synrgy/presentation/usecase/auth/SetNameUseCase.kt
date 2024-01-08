package com.synrgy.presentation.usecase.auth

import com.synrgy.domain.repository.AuthRepository
import com.synrgy.domain.usecase.auth.SetNameUseCase
import javax.inject.Inject

class SetNameUseCase @Inject constructor(
    private val authRepository: AuthRepository
): SetNameUseCase {
    override suspend operator fun invoke(name: String) {
        authRepository.setName(name)
    }
}