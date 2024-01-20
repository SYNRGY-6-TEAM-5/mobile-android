package com.synrgy.data.remote

import com.synrgy.data.helper.Helper
import com.synrgy.data.remote.service.RemoteService
import com.synrgy.domain.Resource
import com.synrgy.domain.body.auth.LoginBody
import com.synrgy.domain.body.auth.RegisterBody
import com.synrgy.domain.body.auth.ValidateOtpBody
import com.synrgy.domain.body.user.EditProfileBody
import com.synrgy.domain.repository.GuestRepository
import com.synrgy.domain.response.auth.LoginResponse
import com.synrgy.domain.response.auth.RegisterResponse
import com.synrgy.domain.response.auth.UploadProfileImageResponse
import com.synrgy.domain.response.auth.ValidateOtpResponse
import com.synrgy.domain.response.user.EditProfileResponse
import okhttp3.MultipartBody
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

    override suspend fun editProfile(
        token: String,
        body: EditProfileBody
    ): Resource<EditProfileResponse> {
        val response = remoteService.editProfile("Bearer $token", body)
        val result = response.body()
        return Helper.getResult(response, result)
    }

    override suspend fun uploadProfileImage(
        token: String,
        name: String,
        file: MultipartBody.Part
    ): Resource<UploadProfileImageResponse> {
        val response = remoteService.uploadProfileImage("Bearer $token", file, name)
        val result = response.body()
        return Helper.getResult(response, result)
    }
}