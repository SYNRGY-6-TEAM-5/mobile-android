package com.synrgy.presentation.usecase.onboarding

import com.synrgy.domain.repository.NewUserRepository
import com.synrgy.domain.usecase.onboarding.GetNewUserUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewUserUseCase @Inject constructor(
    private val newUserRepository: NewUserRepository
): GetNewUserUseCase {
    override suspend operator fun invoke(): Flow<Boolean?> {
        return newUserRepository.getIsNewUser()
    }
}