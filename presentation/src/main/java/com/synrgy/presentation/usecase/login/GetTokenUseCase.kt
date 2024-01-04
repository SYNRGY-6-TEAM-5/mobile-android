package com.synrgy.presentation.usecase.login

import com.synrgy.domain.repository.LoginRepository
import com.synrgy.domain.usecase.login.GetTokenUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(
    private val loginRepository: LoginRepository
): GetTokenUseCase {
    override suspend operator fun invoke(): Flow<String?> {
        return loginRepository.getToken()
    }
}