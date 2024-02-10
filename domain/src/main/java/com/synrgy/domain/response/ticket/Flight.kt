package com.synrgy.domain.response.ticket


import com.google.gson.annotations.SerializedName

data class Flight(
    @SerializedName("airline")
    val airline: Airline?,
    @SerializedName("airline_id")
    val airlineId: Int?,
    @SerializedName("arrival")
    val arrival: Arrival?,
    @SerializedName("arrival_id")
    val arrivalId: Int?,
    @SerializedName("business_seat")
    val businessSeat: Int?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("departure")
    val departure: Departure?,
    @SerializedName("departure_id")
    val departureId: Int?,
    @SerializedName("economy_seat")
    val economySeat: Int?,
    @SerializedName("first_seat")
    val firstSeat: Int?,
    @SerializedName("flight_id")
    val flightId: Int?,
    @SerializedName("flight_number")
    val flightNumber: String?,
    @SerializedName("flight_status")
    val flightStatus: String?,
    @SerializedName("iata")
    val iata: String?,
    @SerializedName("transit")
    val transit: Int?,
    @SerializedName("updated_at")
    val updatedAt: String?
)