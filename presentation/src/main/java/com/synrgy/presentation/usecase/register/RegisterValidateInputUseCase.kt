package com.synrgy.presentation.usecase.register

import com.synrgy.domain.repository.RegisterRepository
import com.synrgy.domain.usecase.register.RegisterValidateInputUseCase
import javax.inject.Inject

class RegisterValidateInputUseCase @Inject constructor(
    private val registerRepository: RegisterRepository
): RegisterValidateInputUseCase {
    override suspend operator fun invoke(email: String, password: String): Boolean {
        return registerRepository.validateInput(email, password)
    }
}