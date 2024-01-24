package com.synrgy.presentation.usecase.airport

import com.synrgy.domain.repository.AuthRepository
import com.synrgy.domain.response.airport.AirportData
import com.synrgy.domain.usecase.airport.SetRecentAirportUseCase
import javax.inject.Inject

class SetRecentAirportUseCase @Inject constructor(
    private val authRepository: AuthRepository
): SetRecentAirportUseCase {
    override suspend operator fun invoke(data: AirportData) {
        return authRepository.setRecentAirport(data)
    }
}