package com.synrgy.domain.usecase.forgotpassword

import com.synrgy.domain.Resource
import com.synrgy.domain.body.forgotpassword.ForgotPasswordBody
import com.synrgy.domain.response.forgotpassword.ForgotPasswordResponse

interface ForgotPasswordUseCase {
    suspend fun invoke(
        body: ForgotPasswordBody
    ): Resource<ForgotPasswordResponse>
}