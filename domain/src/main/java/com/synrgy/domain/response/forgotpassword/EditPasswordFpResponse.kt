package com.synrgy.domain.response.forgotpassword


import com.google.gson.annotations.SerializedName

data class EditPasswordFpResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?
)