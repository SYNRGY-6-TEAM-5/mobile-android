package com.synrgy.presentation.usecase.auth

import com.synrgy.domain.repository.AuthRepository
import com.synrgy.domain.usecase.auth.GetUserIdUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserIdUseCase @Inject constructor(
    private val authRepository: AuthRepository
): GetUserIdUseCase {
    override suspend operator fun invoke(): Flow<String?> {
        return authRepository.getUserId()
    }
}