package com.synrgy.presentation.usecase.register

import com.synrgy.domain.Resource
import com.synrgy.domain.body.ValidateOtpBody
import com.synrgy.domain.repository.GuestRepository
import com.synrgy.domain.response.ValidateOtpResponse
import com.synrgy.domain.usecase.register.ValidateOtpUseCase
import javax.inject.Inject

class ValidateOtpUseCase @Inject constructor(
    private val guestRepository: GuestRepository
): ValidateOtpUseCase {
    override suspend operator fun invoke(body: ValidateOtpBody): Resource<ValidateOtpResponse> {
        return guestRepository.validateOtp(body)
    }
}