package com.synrgy.data.remote.service

import com.synrgy.domain.response.ticket.GetTicketsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NodeService {
    @GET("ticket")
    suspend fun getTickets(
        @Query("departure_airport") departureAirport: String,
        @Query("arrival_airport") arrivalAirport: String,
        @Query("departure_date") departureDate: String
    ): Response<GetTicketsResponse>
}