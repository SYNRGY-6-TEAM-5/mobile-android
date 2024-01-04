package com.zachriek.domain.usecase.login

import com.synrgy.domain.Login
import com.synrgy.domain.LoginBody

interface LoginUseCase {
    suspend fun invoke(
        user: LoginBody
    ): Login
}