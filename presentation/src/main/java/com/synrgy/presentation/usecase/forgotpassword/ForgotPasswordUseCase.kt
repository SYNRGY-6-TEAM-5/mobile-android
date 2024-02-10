package com.synrgy.presentation.usecase.forgotpassword

import com.synrgy.domain.Resource
import com.synrgy.domain.body.forgotpassword.ForgotPasswordBody
import com.synrgy.domain.repository.GuestRepository
import com.synrgy.domain.response.forgotpassword.ForgotPasswordResponse
import com.synrgy.domain.usecase.forgotpassword.ForgotPasswordUseCase
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(
    private val guestRepository: GuestRepository
): ForgotPasswordUseCase {
    override suspend operator fun invoke(body: ForgotPasswordBody): Resource<ForgotPasswordResponse> {
        return guestRepository.forgotPassword(body)
    }
}