package com.synrgy.domain.repository

import com.synrgy.domain.Resource
import com.synrgy.domain.response.ticket.GetTicketsResponse

interface TicketRepository {
    suspend fun getTickets(
        departureAirport: String,
        arrivalAirport: String,
        departureDate: String
    ): Resource<GetTicketsResponse>
}