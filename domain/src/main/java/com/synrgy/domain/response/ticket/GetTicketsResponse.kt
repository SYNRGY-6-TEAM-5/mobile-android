package com.synrgy.domain.response.ticket


import com.google.gson.annotations.SerializedName

data class GetTicketsResponse(
    @SerializedName("data")
    val `data`: List<TicketData>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("meta")
    val meta: Meta?
)