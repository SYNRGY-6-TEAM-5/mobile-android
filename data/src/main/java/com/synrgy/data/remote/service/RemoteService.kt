package com.synrgy.data.remote.service

import com.synrgy.domain.body.auth.LoginBody
import com.synrgy.domain.body.auth.RegisterBody
import com.synrgy.domain.body.auth.ValidateOtpBody
import com.synrgy.domain.body.user.EditProfileBody
import com.synrgy.domain.response.auth.LoginResponse
import com.synrgy.domain.response.auth.RegisterResponse
import com.synrgy.domain.response.auth.UploadProfileImageResponse
import com.synrgy.domain.response.auth.ValidateOtpResponse
import com.synrgy.domain.response.user.EditProfileResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query

interface RemoteService {
    @POST("auth/signup")
    suspend fun register(@Body user: RegisterBody): Response<RegisterResponse>

    @POST("auth/signup/validate-otp")
    suspend fun validateOtp(@Body body: ValidateOtpBody): Response<ValidateOtpResponse>

    @POST("auth/login")
    suspend fun login(@Body body: LoginBody): Response<LoginResponse>

    @PUT("user/profile")
    suspend fun editProfile(
        @Header("Authorization") token: String,
        @Body body: EditProfileBody,
    ): Response<EditProfileResponse>

    @Multipart
    @POST("user/profile-image")
    suspend fun uploadProfileImage(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Query("name") name: String,
    ): Response<UploadProfileImageResponse>
}