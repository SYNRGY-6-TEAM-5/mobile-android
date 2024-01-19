package com.synrgy.domain.response


import com.google.gson.annotations.SerializedName

data class ExceptionResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)