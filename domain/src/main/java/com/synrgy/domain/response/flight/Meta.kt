package com.synrgy.domain.response.flight


import com.google.gson.annotations.SerializedName

data class Meta(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("size")
    val size: Int?,
    @SerializedName("totalData")
    val totalData: Int?,
    @SerializedName("totalPages")
    val totalPages: Int?
)