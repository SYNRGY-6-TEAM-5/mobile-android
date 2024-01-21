package com.synrgy.domain.body.forgotpassword


import com.google.gson.annotations.SerializedName

data class EditPasswordFpBody(
    @SerializedName("newPassword")
    val newPassword: String?,
    @SerializedName("retypePassword")
    val retypePassword: String?
)