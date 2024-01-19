package com.synrgy.domain.usecase.login

import com.synrgy.domain.Login
import com.synrgy.domain.body.LoginBody

interface LoginUseCase {
    suspend fun invoke(
        user: LoginBody
    ): Login
}