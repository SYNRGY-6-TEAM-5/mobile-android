package com.synrgy.domain.response.ticket


import com.google.gson.annotations.SerializedName

data class Arrival(
    @SerializedName("airport_details")
    val airportDetails: AirportDetails?,
    @SerializedName("airport_id")
    val airportId: Int?,
    @SerializedName("arrival_id")
    val arrivalId: Int?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("scheduled_time")
    val scheduledTime: String?,
    @SerializedName("terminal")
    val terminal: String?,
    @SerializedName("updated_at")
    val updatedAt: Any?
)