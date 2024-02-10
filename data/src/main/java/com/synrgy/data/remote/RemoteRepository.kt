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
import com.synrgy.domain.response.error.ExceptionResponse
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
        return try {
            val response = remoteService.register(user)
            val result = response.body()
            Helper.getResult(response, result)
        } catch (_: Exception) {
            Resource.ExceptionRes(Helper.getErrorResponse(
                "",
                ExceptionResponse::class.java
            ))
        }
    }

    override suspend fun validateOtp(body: ValidateOtpBody): Resource<ValidateOtpResponse> {
        return try {
            val response = remoteService.validateOtp(body)
            val result = response.body()
            Helper.getResult(response, result)
        } catch (_: Exception) {
            Resource.ExceptionRes(Helper.getErrorResponse(
                "",
                ExceptionResponse::class.java
            ))
        }
    }

    override suspend fun login(user: LoginBody): Resource<LoginResponse> {
        return try {
            val response = remoteService.login(user)
            val result = response.body()
            Helper.getResult(response, result)
        } catch (_: Exception) {
            Resource.ExceptionRes(Helper.getErrorResponse(
                "",
                ExceptionResponse::class.java
            ))
        }
    }

    override suspend fun editProfile(
        token: String,
        body: EditProfileBody
    ): Resource<EditProfileResponse> {
        return try {
            val response = remoteService.editProfile("Bearer $token", body)
            val result = response.body()
            Helper.getResult(response, result)
        } catch (_: Exception) {
            Resource.ExceptionRes(Helper.getErrorResponse(
                "",
                ExceptionResponse::class.java
            ))
        }
    }

    override suspend fun uploadProfileImage(
        token: String,
        name: String,
        file: MultipartBody.Part
    ): Resource<UploadProfileImageResponse> {
        return try {
            val response = remoteService.uploadProfileImage("Bearer $token", file, name)
            val result = response.body()
            Helper.getResult(response, result)
        } catch (_: Exception) {
            Resource.ExceptionRes(Helper.getErrorResponse(
                "",
                ExceptionResponse::class.java
            ))
        }
    }

    override suspend fun editProfileImage(
        token: String,
        file: MultipartBody.Part,
    ): Resource<EditProfileImageResponse> {
        return try {
            val response = remoteService.editProfileImage("Bearer $token", file)
            val result = response.body()
            Helper.getResult(response, result)
        } catch (_: Exception) {
            Resource.ExceptionRes(Helper.getErrorResponse(
                "",
                ExceptionResponse::class.java
            ))
        }
    }

    override suspend fun forgotPassword(body: ForgotPasswordBody): Resource<ForgotPasswordResponse> {
        return try {
            val response = remoteService.forgotPassword(body)
            val result = response.body()
            Helper.getResult(response, result)
        } catch (_: Exception) {
            Resource.ExceptionRes(Helper.getErrorResponse(
                "",
                ExceptionResponse::class.java
            ))
        }
    }

    override suspend fun validateOtpFp(body: ValidateOtpFpBody): Resource<ValidateOtpFpResponse> {
        return try {
            val response = remoteService.validateOtpFp(body)
            val result = response.body()
            Helper.getResult(response, result)
        } catch (_: Exception) {
            Resource.ExceptionRes(Helper.getErrorResponse(
                "",
                ExceptionResponse::class.java
            ))
        }
    }

    override suspend fun editPasswordFp(
        token: String,
        body: EditPasswordFpBody
    ): Resource<EditPasswordFpResponse> {
        return try {
            val response = remoteService.editPasswordFp("Bearer $token", body)
            val result = response.body()
            Helper.getResult(response, result)
        } catch (_: Exception) {
            Resource.ExceptionRes(Helper.getErrorResponse(
                "",
                ExceptionResponse::class.java
            ))
        }
    }

    override suspend fun getDeparture(): Resource<DepartureResponse> {
        return try {
            val response = remoteService.getDeparture()
            val result = response.body()
            Helper.getResult(response, result)
        } catch (_: Exception) {
            Resource.ExceptionRes(Helper.getErrorResponse(
                "",
                ExceptionResponse::class.java
            ))
        }
    }

    override suspend fun airportList(): Resource<AirportResponse> {
        return try {
            val response = remoteService.getAirport()
            val result = response.body()
            Helper.getResult(response, result)
        } catch (_: Exception) {
            Resource.ExceptionRes(Helper.getErrorResponse(
                "",
                ExceptionResponse::class.java
            ))
        }
    }

    override suspend fun getUserDetail(token: String): Resource<UserDetailResponse> {
        return try {
            val response = remoteService.getUserDetail("Bearer $token")
            val result = response.body()
            Helper.getResult(response, result)
        } catch (_: Exception) {
            Resource.ExceptionRes(Helper.getErrorResponse(
                "",
                ExceptionResponse::class.java
            ))
        }
    }
}