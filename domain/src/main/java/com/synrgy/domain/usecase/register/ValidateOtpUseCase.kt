package com.synrgy.domain.usecase.register

import com.synrgy.domain.Resource
import com.synrgy.domain.body.auth.ValidateOtpBody
import com.synrgy.domain.response.auth.ValidateOtpResponse

interface ValidateOtpUseCase {
    suspend fun invoke(
        body: ValidateOtpBody
    ): Resource<ValidateOtpResponse>
}