package com.synrgy.domain.response


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("email")
    val email: String,
    @SerializedName("roles")
    val roles: List<String>,
    @SerializedName("token")
    val token: String,
    @SerializedName("type")
    val type: String
)