package com.synrgy.domain.body.forgotpassword


data class ValidateOtpFpBody(
    val email: String,
    val otp: String
)