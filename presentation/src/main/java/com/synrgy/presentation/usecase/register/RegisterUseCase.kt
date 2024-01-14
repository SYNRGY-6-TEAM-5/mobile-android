package com.synrgy.presentation.usecase.register

import com.synrgy.domain.Register
import com.synrgy.domain.RegisterBody
import com.synrgy.domain.repository.GuestRepository
import com.synrgy.domain.usecase.register.RegisterUseCase
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val guestRepository: GuestRepository
): RegisterUseCase {
    override suspend operator fun invoke(user: RegisterBody): Register {
        return guestRepository.register(user)
    }
}