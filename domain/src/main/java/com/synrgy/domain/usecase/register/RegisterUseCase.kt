package com.synrgy.domain.usecase.register

import com.synrgy.domain.Resource
import com.synrgy.domain.body.RegisterBody
import com.synrgy.domain.response.RegisterResponse

interface RegisterUseCase {
    suspend fun invoke(
        user: RegisterBody
    ): Resource<RegisterResponse>
}