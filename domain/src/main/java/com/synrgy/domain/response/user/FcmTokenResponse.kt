package com.synrgy.domain.response.user


import com.google.gson.annotations.SerializedName

data class FcmTokenResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?
)