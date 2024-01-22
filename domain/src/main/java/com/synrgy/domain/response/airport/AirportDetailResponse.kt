package com.synrgy.domain.response.airport


import com.google.gson.annotations.SerializedName

data class AirportDetailResponse(
    @SerializedName("airport")
    val airport: Airport?,
    @SerializedName("arrivals")
    val arrivals: List<Arrival>?,
    @SerializedName("departures")
    val departures: List<Departure>?,
    @SerializedName("status")
    val status: Boolean?
)