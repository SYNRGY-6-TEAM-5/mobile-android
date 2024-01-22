package com.synrgy.presentation.usecase.auth

import com.synrgy.domain.repository.AuthRepository
import com.synrgy.domain.usecase.auth.GetRegTokenUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRegTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
): GetRegTokenUseCase {
    override suspend operator fun invoke(): Flow<String?> {
        return authRepository.getRegToken()
    }
}