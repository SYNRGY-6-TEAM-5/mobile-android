package com.synrgy.domain.response


import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("expiredOTP")
    val expiredOTP: Long,
    @SerializedName("otp")
    val otp: String,
    @SerializedName("success")
    val success: Boolean
)