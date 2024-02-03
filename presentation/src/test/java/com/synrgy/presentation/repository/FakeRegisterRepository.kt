package com.synrgy.presentation.repository

import com.synrgy.domain.repository.RegisterRepository
import com.synrgy.presentation.helper.Helper

class FakeRegisterRepository: RegisterRepository {
    override suspend fun validateRegister(email: String, password: String): Boolean {
        return email.isNotEmpty()
                && email.isNotBlank()
                && Helper.isValidEmail(email)
                && password.isNotEmpty()
                && password.isNotBlank()
                && Helper.checkPasswordLength(password)
                && Helper.containsSpecialCharacter(password)
                && Helper.containsAlphanumeric(password)
                && Helper.containsUppercaseLetter(password)
    }
}