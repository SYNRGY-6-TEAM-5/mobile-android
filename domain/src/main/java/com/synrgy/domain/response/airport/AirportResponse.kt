package com.synrgy.domain.response.airport


import com.google.gson.annotations.SerializedName

data class AirportResponse(
    @SerializedName("data")
    val `data`: List<AirportData?>?,
    @SerializedName("status")
    val status: Boolean?
)