package com.synrgy.domain.body.forgotpassword


data class EditPasswordFpBody(
    val newPassword: String,
    val retypePassword: String
)