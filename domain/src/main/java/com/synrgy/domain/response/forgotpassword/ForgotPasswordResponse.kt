package com.synrgy.domain.response.forgotpassword


import com.google.gson.annotations.SerializedName

data class ForgotPasswordResponse(
    @SerializedName("expiredOTP")
    val expiredOTP: Long?,
    @SerializedName("otp")
    val otp: String?,
    @SerializedName("success")
    val success: Boolean?,
    val email: String?
)