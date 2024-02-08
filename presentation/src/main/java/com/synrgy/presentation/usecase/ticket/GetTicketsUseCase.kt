package com.synrgy.presentation.usecase.ticket

import com.synrgy.domain.Resource
import com.synrgy.domain.repository.TicketRepository
import com.synrgy.domain.response.ticket.GetTicketsResponse
import com.synrgy.domain.usecase.ticket.GetTicketsUseCase
import javax.inject.Inject

class GetTicketsUseCase @Inject constructor(
    private val ticketRepository: TicketRepository
): GetTicketsUseCase {
    override suspend operator fun invoke(
        departureAirport: String,
        arrivalAirport: String,
        departureDate: String
    ): Resource<GetTicketsResponse> {
        return ticketRepository.getTickets(departureAirport, arrivalAirport, departureDate)
    }
}