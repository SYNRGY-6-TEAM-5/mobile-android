package com.synrgy.domain.response.auth


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("email")
    val email: String,
    @SerializedName("roles")
    val roles: List<String>,
    @SerializedName("token")
    val token: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("message")
    val message: String?,
)