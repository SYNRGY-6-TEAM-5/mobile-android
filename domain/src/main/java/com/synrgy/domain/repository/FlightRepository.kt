package com.synrgy.domain.repository

import com.synrgy.domain.Resource
import com.synrgy.domain.response.flight.FlightResponse

interface FlightRepository {
    suspend fun getFlights(
        departureAirport: String,
        arrivalAirport: String,
        departureDate: String
    ): Resource<FlightResponse>
}