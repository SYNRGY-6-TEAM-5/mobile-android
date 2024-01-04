package com.synrgy.presentation.usecase.login

import com.synrgy.domain.repository.LoginRepository
import com.synrgy.domain.usecase.login.SetTokenUseCase
import javax.inject.Inject

class SetTokenUseCase @Inject constructor(
    private val loginRepository: LoginRepository
): SetTokenUseCase {
    override suspend operator fun invoke(token: String) {
        loginRepository.setToken(token)
    }
}