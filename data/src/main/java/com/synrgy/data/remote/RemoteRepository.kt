package com.synrgy.data.remote

import com.synrgy.data.helper.Helper
import com.synrgy.data.remote.service.RemoteService
import com.synrgy.domain.Resource
import com.synrgy.domain.body.auth.LoginBody
import com.synrgy.domain.body.auth.RegisterBody
import com.synrgy.domain.body.auth.ValidateOtpBody
import com.synrgy.domain.body.forgotpassword.EditPasswordFpBody
import com.synrgy.domain.body.forgotpassword.ForgotPasswordBody
import com.synrgy.domain.body.forgotpassword.ValidateOtpFpBody
import com.synrgy.domain.body.user.EditProfileBody
import com.synrgy.domain.repository.AirportRepository
import com.synrgy.domain.repository.DepartureRepository
import com.synrgy.domain.repository.GuestRepository
import com.synrgy.domain.repository.UserRepository
import com.synrgy.domain.response.airport.AirportResponse
import com.synrgy.domain.response.auth.LoginResponse
import com.synrgy.domain.response.auth.RegisterResponse
import com.synrgy.domain.response.auth.UploadProfileImageResponse
import com.synrgy.domain.response.auth.ValidateOtpResponse
import com.synrgy.domain.response.departure.DepartureResponse
import com.synrgy.domain.response.forgotpassword.EditPasswordFpResponse
import com.synrgy.domain.response.forgotpassword.ForgotPasswordResponse
import com.synrgy.domain.response.forgotpassword.ValidateOtpFpResponse
import com.synrgy.domain.response.user.EditProfileImageResponse
import com.synrgy.domain.response.user.EditProfileResponse
import com.synrgy.domain.response.user.UserDetailResponse
import okhttp3.MultipartBody
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val remoteService: RemoteService
): GuestRepository, DepartureRepository, AirportRepository, UserRepository {
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

    override suspend fun editProfileImage(
        token: String,
        file: MultipartBody.Part,
    ): Resource<EditProfileImageResponse> {
        val response = remoteService.editProfileImage("Bearer $token", file)
        val result = response.body()
        return Helper.getResult(response, result)
    }

    override suspend fun forgotPassword(body: ForgotPasswordBody): Resource<ForgotPasswordResponse> {
        val response = remoteService.forgotPassword(body)
        val result = response.body()
        return Helper.getResult(response, result)
    }

    override suspend fun validateOtpFp(body: ValidateOtpFpBody): Resource<ValidateOtpFpResponse> {
        val response = remoteService.validateOtpFp(body)
        val result = response.body()
        return Helper.getResult(response, result)
    }

    override suspend fun editPasswordFp(
        token: String,
        body: EditPasswordFpBody
    ): Resource<EditPasswordFpResponse> {
        val response = remoteService.editPasswordFp("Bearer $token", body)
        val result = response.body()
        return Helper.getResult(response, result)
    }

    override suspend fun getDeparture(): Resource<DepartureResponse> {
        val response = remoteService.getDeparture()
        val result = response.body()
        return Helper.getResult(response, result)
    }

    override suspend fun airportList(): Resource<AirportResponse> {
        val response = remoteService.getAirport()
        val result = response.body()
        return Helper.getResult(response, result)
    }

    override suspend fun getUserDetail(token: String): Resource<UserDetailResponse> {
        val response = remoteService.getUserDetail("Bearer $token")
        val result = response.body()
        return Helper.getResult(response, result)
    }
}