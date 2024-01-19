package com.synrgy.data.remote

import com.synrgy.data.helper.Helper
import com.synrgy.data.remote.service.RemoteService
import com.synrgy.domain.Resource
import com.synrgy.domain.body.LoginBody
import com.synrgy.domain.body.RegisterBody
import com.synrgy.domain.body.ValidateOtpBody
import com.synrgy.domain.repository.GuestRepository
import com.synrgy.domain.response.LoginResponse
import com.synrgy.domain.response.RegisterResponse
import com.synrgy.domain.response.ValidateOtpResponse
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val remoteService: RemoteService
): GuestRepository {
    override suspend fun register(user: RegisterBody): Resource<RegisterResponse> {
        val response = remoteService.register(user)
        val result = response.body()
        return Helper.getResult(response, result)
    }

    override suspend fun validateOtp(body: ValidateOtpBody): Resource<ValidateOtpResponse> {
        val response = remoteService.validateOtp(body)
        val result = response.body()
        return Helper.getResult(response, result)
    }

    override suspend fun login(user: LoginBody): Resource<LoginResponse> {
        val response = remoteService.login(user)
        val result = response.body()
        return Helper.getResult(response, result)
    }
}