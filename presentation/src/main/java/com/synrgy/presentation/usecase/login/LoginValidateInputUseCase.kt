package com.synrgy.presentation.usecase.login

import com.synrgy.domain.repository.LoginRepository
import com.synrgy.domain.usecase.login.LoginValidateInputUseCase
import javax.inject.Inject

class LoginValidateInputUseCase @Inject constructor(
    private val loginRepository: LoginRepository
): LoginValidateInputUseCase {
    override suspend operator fun invoke(email: String, password: String): Boolean {
        return loginRepository.validateInput(email, password)
    }
}