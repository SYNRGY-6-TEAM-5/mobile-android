package com.synrgy.presentation.usecase.login

import com.synrgy.domain.Login
import com.synrgy.domain.LoginBody
import com.synrgy.domain.repository.GuestRepository
import com.zachriek.domain.usecase.login.LoginUseCase
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val guestRepository: GuestRepository
): LoginUseCase {
    override suspend operator fun invoke(user: LoginBody): Login {
        return guestRepository.login(user)
    }
}