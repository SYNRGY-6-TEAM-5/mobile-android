package com.synrgy.domain.body.auth

data class ValidateOtpBody(
    val email: String,
    val otp: String,
    val password: String
)