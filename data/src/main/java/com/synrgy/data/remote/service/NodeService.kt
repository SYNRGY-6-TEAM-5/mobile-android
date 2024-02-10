package com.synrgy.data.remote.service

import com.synrgy.domain.response.flight.FlightResponse
import com.synrgy.domain.response.ticket.GetTicketsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NodeService {
    @GET("ticket")
    suspend fun getTickets(
        @Query("departure_airport") departureAirport: String,
        @Query("arrival_airport") arrivalAirport: String,
        @Query("departure_date") departureDate: String
    ): Response<GetTicketsResponse>

    @GET("ticket/{id}")
    suspend fun getTicketsById(
        @Path("id") id: Int
    ): Response<GetTicketsResponse>

    @GET("flight")
    suspend fun getFlights(
        @Query("departure_airport") departureAirport: String,
        @Query("arrival_airport") arrivalAirport: String,
        @Query("departure_date") departureDate: String
    ): Response<FlightResponse>
}