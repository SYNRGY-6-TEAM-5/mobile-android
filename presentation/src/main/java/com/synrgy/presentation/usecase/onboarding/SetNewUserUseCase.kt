package com.synrgy.presentation.usecase.onboarding

import com.synrgy.domain.repository.NewUserRepository
import com.synrgy.domain.usecase.onboarding.SetNewUserUseCase
import javax.inject.Inject

class SetNewUserUseCase @Inject constructor(
    private val newUserRepository: NewUserRepository
): SetNewUserUseCase {
    override suspend operator fun invoke(isNewUser: Boolean) {
        newUserRepository.setIsNewUser(isNewUser)
    }
}