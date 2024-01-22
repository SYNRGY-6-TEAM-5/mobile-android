package com.synrgy.domain.response.forgotpassword


import com.google.gson.annotations.SerializedName

data class ValidateOtpFpResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?,
    @SerializedName("token")
    val token: String?
)