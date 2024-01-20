package com.synrgy.domain.response.auth


import com.google.gson.annotations.SerializedName

data class ValidateOtpResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("token")
    val token: String
)