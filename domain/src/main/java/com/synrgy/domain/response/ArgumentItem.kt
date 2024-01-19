package com.synrgy.domain.response


import com.google.gson.annotations.SerializedName

data class ArgumentItem(
    @SerializedName("arguments")
    val arguments: Any,
    @SerializedName("code")
    val code: String,
    @SerializedName("codes")
    val codes: List<String>,
    @SerializedName("defaultMessage")
    val defaultMessage: String
)