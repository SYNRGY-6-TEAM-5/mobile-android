package com.synrgy.domain.local

data class TicketDetails(
    val id: String,
    val origin: String,
    val destination: String,
    val depDate: String,
    val arrivalDate: String,
    val oAirportName: String,
    val dAirportName: String,
    val oTerminal: String,
    val dTerminal: String,
    val airlineName: String,
    val airlineCode: String,
    val airlineImage: String,
    val status: String
)
