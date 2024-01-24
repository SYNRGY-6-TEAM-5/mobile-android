package com.synrgy.domain.repository

import com.synrgy.domain.Resource
import com.synrgy.domain.response.airport.AirportResponse

interface AirportRepository {
    suspend fun airportList(): Resource<AirportResponse>
}