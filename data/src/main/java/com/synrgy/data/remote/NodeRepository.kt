package com.synrgy.data.remote

import com.synrgy.data.helper.Helper
import com.synrgy.data.remote.service.NodeService
import com.synrgy.domain.Resource
import com.synrgy.domain.repository.TicketRepository
import com.synrgy.domain.response.error.ExceptionResponse
import com.synrgy.domain.response.ticket.GetTicketsResponse
import javax.inject.Inject

class NodeRepository @Inject constructor(
    private val nodeService: NodeService
): TicketRepository {
    override suspend fun getTickets(
        departureAirport: String,
        arrivalAirport: String,
        departureDate: String,
    ): Resource<GetTicketsResponse> {
        return try {
            val response = nodeService.getTickets(departureAirport, arrivalAirport, departureDate)
            val result = response.body()
            Helper.getResult(response, result)
        } catch (_: Exception) {
            Resource.ExceptionRes(
                Helper.getErrorResponse(
                "",
                ExceptionResponse::class.java
            ))
        }
    }
}