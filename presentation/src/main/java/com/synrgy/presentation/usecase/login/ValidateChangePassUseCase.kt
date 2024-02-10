package com.synrgy.presentation.usecase.login

import com.synrgy.domain.repository.LoginRepository
import com.synrgy.domain.usecase.login.ValidateChangePassUseCase
import javax.inject.Inject

class ValidateChangePassUseCase @Inject constructor(
    private val loginRepository: LoginRepository
): ValidateChangePassUseCase {
    override suspend operator fun invoke(newPassword: String, retypePassword: String): Boolean {
        return loginRepository.validateChangePass(newPassword, retypePassword)
    }
}