package com.synrgy.data.remote.service

import com.synrgy.domain.body.auth.LoginBody
import com.synrgy.domain.body.auth.RegisterBody
import com.synrgy.domain.body.auth.ValidateOtpBody
import com.synrgy.domain.body.forgotpassword.EditPasswordFpBody
import com.synrgy.domain.body.forgotpassword.ForgotPasswordBody
import com.synrgy.domain.body.forgotpassword.ValidateOtpFpBody
import com.synrgy.domain.body.user.EditProfileBody
import com.synrgy.domain.response.airport.AirportDetailResponse
import com.synrgy.domain.response.airport.AirportResponse
import com.synrgy.domain.response.auth.LoginResponse
import com.synrgy.domain.response.auth.RegisterResponse
import com.synrgy.domain.response.auth.UploadProfileImageResponse
import com.synrgy.domain.response.auth.ValidateOtpResponse
import com.synrgy.domain.response.departure.DepartureResponse
import com.synrgy.domain.response.forgotpassword.EditPasswordFpResponse
import com.synrgy.domain.response.forgotpassword.ForgotPasswordResponse
import com.synrgy.domain.response.forgotpassword.ValidateOtpFpResponse
import com.synrgy.domain.response.user.EditProfileResponse
import com.synrgy.domain.response.user.UserDetailResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
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
        @Body body: EditProfileBody
    ): Response<EditProfileResponse>

    @Multipart
    @POST("user/profile-image")
    suspend fun uploadProfileImage(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Query("name") name: String
    ): Response<UploadProfileImageResponse>

    @POST("auth/forgot-password")
    suspend fun forgotPassword(@Body body: ForgotPasswordBody): Response<ForgotPasswordResponse>

    @POST("auth/forgot-password/validate-otp")
    suspend fun validateOtpFp(@Body body: ValidateOtpFpBody): Response<ValidateOtpFpResponse>

    @PUT("auth/forgot-password/edit-password")
    suspend fun editPasswordFp(
        @Header("Authorization") token: String,
        @Body body: EditPasswordFpBody
    ): Response<EditPasswordFpResponse>

    @GET("departure")
    suspend fun getDeparture(): Response<DepartureResponse>

    @GET("airport")
    suspend fun getAirport(): Response<AirportResponse>

    @GET("airport/{id}/departures-arrivals")
    suspend fun getAirportDetail(@Path("id") id: Int): Response<AirportDetailResponse>

    @GET("user/detail-user")
    suspend fun getUserDetail(@Header("Authorization") token: String): Response<UserDetailResponse>
}