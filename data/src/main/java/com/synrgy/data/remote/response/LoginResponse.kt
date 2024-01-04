package com.zachriek.data.remote.response


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("data")
    val data: LoginResponseItem,
    @SerializedName("message")
    val message: String
)