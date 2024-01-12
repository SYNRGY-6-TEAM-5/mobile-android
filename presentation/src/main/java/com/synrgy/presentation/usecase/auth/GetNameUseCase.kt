package com.synrgy.presentation.usecase.auth

import com.synrgy.domain.repository.AuthRepository
import com.synrgy.domain.usecase.auth.GetNameUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNameUseCase @Inject constructor(
    private val authRepository: AuthRepository
): GetNameUseCase {
    override suspend operator fun invoke(): Flow<String?> {
        return authRepository.getName()
    }
}