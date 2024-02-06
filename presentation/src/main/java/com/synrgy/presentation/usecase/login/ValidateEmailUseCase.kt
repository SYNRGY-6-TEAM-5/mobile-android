package com.synrgy.presentation.usecase.login

import com.synrgy.domain.repository.LoginRepository
import com.synrgy.domain.usecase.login.ValidateEmailUseCase
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor(
    private val loginRepository: LoginRepository
): ValidateEmailUseCase {
    override suspend operator fun invoke(email: String): Boolean {
        return loginRepository.validateEmail(email)
    }
}