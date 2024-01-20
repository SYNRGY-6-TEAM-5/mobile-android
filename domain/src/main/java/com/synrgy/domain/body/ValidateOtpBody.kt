package com.synrgy.domain.body

data class ValidateOtpBody(
    val email: String,
    val otp: String,
    val password: String
)