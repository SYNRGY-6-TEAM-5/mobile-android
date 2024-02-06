package com.synrgy.domain.repository

import com.synrgy.domain.Resource
import com.synrgy.domain.body.auth.LoginBody
import com.synrgy.domain.body.auth.RegisterBody
import com.synrgy.domain.body.auth.ValidateOtpBody
import com.synrgy.domain.body.forgotpassword.EditPasswordFpBody
import com.synrgy.domain.body.forgotpassword.ForgotPasswordBody
import com.synrgy.domain.body.forgotpassword.ValidateOtpFpBody
import com.synrgy.domain.body.user.EditProfileBody
import com.synrgy.domain.response.auth.LoginResponse
import com.synrgy.domain.response.auth.RegisterResponse
import com.synrgy.domain.response.auth.UploadProfileImageResponse
import com.synrgy.domain.response.auth.ValidateOtpResponse
import com.synrgy.domain.response.forgotpassword.EditPasswordFpResponse
import com.synrgy.domain.response.forgotpassword.ForgotPasswordResponse
import com.synrgy.domain.response.forgotpassword.ValidateOtpFpResponse
import com.synrgy.domain.response.user.EditProfileResponse
import okhttp3.MultipartBody

interface GuestRepository {
    suspend fun register(user: RegisterBody): Resource<RegisterResponse>

    suspend fun validateOtp(body: ValidateOtpBody): Resource<ValidateOtpResponse>

    suspend fun login(user: LoginBody): Resource<LoginResponse>

    suspend fun forgotPassword(body: ForgotPasswordBody): Resource<ForgotPasswordResponse>

    suspend fun validateOtpFp(body: ValidateOtpFpBody): Resource<ValidateOtpFpResponse>

    suspend fun editPasswordFp(
        token: String,
        body: EditPasswordFpBody
    ): Resource<EditPasswordFpResponse>
}

