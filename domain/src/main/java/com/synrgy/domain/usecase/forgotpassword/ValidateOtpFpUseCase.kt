package com.synrgy.domain.usecase.forgotpassword

import com.synrgy.domain.Resource
import com.synrgy.domain.body.forgotpassword.ValidateOtpFpBody
import com.synrgy.domain.response.forgotpassword.ValidateOtpFpResponse

interface ValidateOtpFpUseCase {
    suspend fun invoke(
        body: ValidateOtpFpBody
    ): Resource<ValidateOtpFpResponse>
}