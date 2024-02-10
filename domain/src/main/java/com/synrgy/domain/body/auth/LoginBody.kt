package com.synrgy.domain.body.auth

data class LoginBody(
    val emailAddress: String,
    val password: String
)
