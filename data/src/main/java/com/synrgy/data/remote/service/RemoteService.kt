package com.synrgy.data.remote.service

import com.synrgy.domain.body.LoginBody
import com.synrgy.domain.body.RegisterBody
import com.synrgy.domain.body.ValidateOtpBody
import com.synrgy.domain.response.LoginResponse
import com.synrgy.domain.response.RegisterResponse
import com.synrgy.domain.response.ValidateOtpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RemoteService {
    @POST("auth/signup")
    suspend fun register(@Body user: RegisterBody): Response<RegisterResponse>

    @POST("auth/signup/validate-otp")
    suspend fun validateOtp(@Body body: ValidateOtpBody): Response<ValidateOtpResponse>

    @POST("auth/login")
    suspend fun login(@Body body: LoginBody): Response<LoginResponse>
}