package com.synrgy.domain.response.user


import com.google.gson.annotations.SerializedName

data class EditProfileResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)