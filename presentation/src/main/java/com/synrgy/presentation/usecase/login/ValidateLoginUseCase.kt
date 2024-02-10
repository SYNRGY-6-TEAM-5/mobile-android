package com.synrgy.presentation.usecase.login

import com.synrgy.domain.repository.LoginRepository
import com.synrgy.domain.usecase.login.ValidateLoginUseCase
import javax.inject.Inject

class ValidateLoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
): ValidateLoginUseCase {
    override suspend operator fun invoke(email: String, password: String): Boolean {
        return loginRepository.validateLogin(email, password)
    }
}