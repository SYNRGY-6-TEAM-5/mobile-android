package com.synrgy.presentation.usecase.register

import com.synrgy.domain.repository.RegisterRepository
import com.synrgy.domain.usecase.register.ValidateRegisterUseCase
import javax.inject.Inject

class ValidateRegisterUseCase @Inject constructor(
    private val registerRepository: RegisterRepository
): ValidateRegisterUseCase {
    override suspend operator fun invoke(email: String, password: String): Boolean {
        return registerRepository.validateRegister(email, password)
    }
}