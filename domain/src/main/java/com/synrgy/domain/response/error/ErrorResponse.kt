package com.synrgy.domain.response.error


import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("error")
    val error: String?,
    @SerializedName("errors")
    val errors: List<ErrorItem?>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("path")
    val path: String?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("timestamp")
    val timestamp: Long?
)