package com.synrgy.domain.usecase.ticket

import com.synrgy.domain.Resource
import com.synrgy.domain.response.ticket.GetTicketsResponse

interface GetTicketsUseCase {
    suspend fun invoke(
        departureAirport: String,
        arrivalAirport: String,
        departureDate: String
    ): Resource<GetTicketsResponse>
}