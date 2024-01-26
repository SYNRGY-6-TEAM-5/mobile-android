package com.synrgy.domain.response.user


import com.google.gson.annotations.SerializedName

data class EditProfileImageResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("urlImage")
    val urlImage: String?
)