package com.synrgy.domain.response


import com.google.gson.annotations.SerializedName

data class ErrorItem(
    @SerializedName("arguments")
    val arguments: List<ArgumentItem>,
    @SerializedName("bindingFailure")
    val bindingFailure: Boolean,
    @SerializedName("code")
    val code: String,
    @SerializedName("codes")
    val codes: List<String>,
    @SerializedName("defaultMessage")
    val defaultMessage: String,
    @SerializedName("field")
    val `field`: String,
    @SerializedName("objectName")
    val objectName: String,
    @SerializedName("rejectedValue")
    val rejectedValue: String
)