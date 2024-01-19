package com.synrgy.presentation.usecase.login

import com.synrgy.domain.Resource
import com.synrgy.domain.body.LoginBody
import com.synrgy.domain.repository.GuestRepository
import com.synrgy.domain.response.LoginResponse
import com.synrgy.domain.usecase.login.LoginUseCase
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val guestRepository: GuestRepository
): LoginUseCase {
    override suspend operator fun invoke(user: LoginBody): Resource<LoginResponse> {
        return guestRepository.login(user)
    }
}