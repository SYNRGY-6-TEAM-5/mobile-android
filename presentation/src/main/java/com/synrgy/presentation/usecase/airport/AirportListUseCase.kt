package com.synrgy.presentation.usecase.airport

import com.synrgy.domain.Resource
import com.synrgy.domain.repository.AirportRepository
import com.synrgy.domain.response.airport.AirportResponse
import com.synrgy.domain.usecase.airport.AirportListUseCase
import javax.inject.Inject

class AirportListUseCase @Inject constructor(
    private val airportRepository: AirportRepository
): AirportListUseCase {
    override suspend operator fun invoke(): Resource<AirportResponse> {
        return airportRepository.airportList()
    }
}