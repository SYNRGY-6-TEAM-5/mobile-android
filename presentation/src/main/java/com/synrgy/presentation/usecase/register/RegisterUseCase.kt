package com.synrgy.presentation.usecase.register

import com.synrgy.domain.Resource
import com.synrgy.domain.body.auth.RegisterBody
import com.synrgy.domain.repository.GuestRepository
import com.synrgy.domain.response.auth.RegisterResponse
import com.synrgy.domain.usecase.register.RegisterUseCase
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val guestRepository: GuestRepository
): RegisterUseCase {
    override suspend operator fun invoke(user: RegisterBody): Resource<RegisterResponse> {
        return guestRepository.register(user)
    }
}