package com.synrgy.domain.local

data class TicketDetails(
    val id: String,
    val origin: String,
    val destination: String,
    val date: String,
    val oAirportName: String,
    val dAirportName: String,
    val oTerminal: String,
    val dTerminal: String
)
