package com.synrgy.domain.usecase.register

import com.synrgy.domain.Register
import com.synrgy.domain.RegisterBody

interface RegisterUseCase {
    suspend fun invoke(
        user: RegisterBody
    ): Register
}