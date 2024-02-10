package com.synrgy.domain.response.flight


import com.google.gson.annotations.SerializedName

data class Departure(
    @SerializedName("airport_details")
    val airportDetails: AirportDetails?,
    @SerializedName("airport_id")
    val airportId: Int?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("departure_id")
    val departureId: Int?,
    @SerializedName("scheduled_time")
    val scheduledTime: String?,
    @SerializedName("terminal")
    val terminal: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
)