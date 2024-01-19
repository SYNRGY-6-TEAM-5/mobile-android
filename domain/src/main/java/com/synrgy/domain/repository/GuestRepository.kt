package com.synrgy.domain.repository

import com.synrgy.domain.Resource
import com.synrgy.domain.body.LoginBody
import com.synrgy.domain.body.RegisterBody
import com.synrgy.domain.body.ValidateOtpBody
import com.synrgy.domain.response.LoginResponse
import com.synrgy.domain.response.RegisterResponse
import com.synrgy.domain.response.ValidateOtpResponse

interface GuestRepository {
    suspend fun register(user: RegisterBody): Resource<RegisterResponse>
    suspend fun validateOtp(body: ValidateOtpBody): Resource<ValidateOtpResponse>
    suspend fun login(user: LoginBody): Resource<LoginResponse>
}

