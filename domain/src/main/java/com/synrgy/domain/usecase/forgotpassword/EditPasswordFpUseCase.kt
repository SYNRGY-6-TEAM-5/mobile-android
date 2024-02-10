package com.synrgy.domain.usecase.forgotpassword

import com.synrgy.domain.Resource
import com.synrgy.domain.body.forgotpassword.EditPasswordFpBody
import com.synrgy.domain.response.forgotpassword.EditPasswordFpResponse

interface EditPasswordFpUseCase {
    suspend fun invoke(
        token: String,
        body: EditPasswordFpBody
    ): Resource<EditPasswordFpResponse>
}