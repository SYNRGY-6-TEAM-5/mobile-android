package com.synrgy.domain.response.ticket


import com.google.gson.annotations.SerializedName

data class TicketData(
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("fare_amount")
    val fareAmount: String?,
    @SerializedName("flight")
    val flight: Flight?,
    @SerializedName("flight_id")
    val flightId: Int?,
    @SerializedName("ticket_amount")
    val ticketAmount: Int?,
    @SerializedName("ticket_id")
    val ticketId: Int?,
    @SerializedName("ticket_type")
    val ticketType: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("valid_until")
    val validUntil: String?
)