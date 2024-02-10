package com.synrgy.domain.response.departure


import com.google.gson.annotations.SerializedName

data class DepartureResponse(
    @SerializedName("data")
    val `data`: List<DepartureData?>?,
    @SerializedName("status")
    val status: Boolean?
)