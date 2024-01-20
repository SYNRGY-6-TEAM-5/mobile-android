package com.synrgy.domain.usecase.login

import com.synrgy.domain.Resource
import com.synrgy.domain.body.auth.LoginBody
import com.synrgy.domain.response.auth.LoginResponse

interface LoginUseCase {
    suspend fun invoke(
        user: LoginBody
    ): Resource<LoginResponse>
}