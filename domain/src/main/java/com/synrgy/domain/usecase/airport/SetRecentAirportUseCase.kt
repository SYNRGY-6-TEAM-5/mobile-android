package com.synrgy.domain.usecase.airport

import com.synrgy.domain.response.airport.AirportData

interface SetRecentAirportUseCase {
    suspend fun invoke(
        data: AirportData
    )
}