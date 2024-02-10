package com.synrgy.presentation.usecase.forgotpassword

import com.synrgy.domain.Resource
import com.synrgy.domain.body.forgotpassword.ValidateOtpFpBody
import com.synrgy.domain.repository.GuestRepository
import com.synrgy.domain.response.forgotpassword.ValidateOtpFpResponse
import com.synrgy.domain.usecase.forgotpassword.ValidateOtpFpUseCase
import javax.inject.Inject

class ValidateOtpFpUseCase @Inject constructor(
    private val guestRepository: GuestRepository
): ValidateOtpFpUseCase {
    override suspend operator fun invoke(body: ValidateOtpFpBody): Resource<ValidateOtpFpResponse> {
        return guestRepository.validateOtpFp(body)
    }
}