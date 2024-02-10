package com.synrgy.domain.response.flight


import com.google.gson.annotations.SerializedName

data class FlightResponse(
    @SerializedName("data")
    val `data`: List<FlightData>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("meta")
    val meta: Meta?
)