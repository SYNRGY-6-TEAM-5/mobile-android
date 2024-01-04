package com.zachriek.data.remote.response


import com.google.gson.annotations.SerializedName

data class LoginResponseItem(
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("token")
    val token: String,
    @SerializedName("username")
    val username: String
)