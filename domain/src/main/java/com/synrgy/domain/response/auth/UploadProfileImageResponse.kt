package com.synrgy.domain.response.auth


import com.google.gson.annotations.SerializedName

data class UploadProfileImageResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("urlImage")
    val urlImage: String?
)