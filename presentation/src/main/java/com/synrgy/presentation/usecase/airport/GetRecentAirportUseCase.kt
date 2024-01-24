package com.synrgy.presentation.usecase.airport

import com.synrgy.domain.repository.AuthRepository
import com.synrgy.domain.usecase.airport.GetRecentAirportUseCase
import javax.inject.Inject

class GetRecentAirportUseCase @Inject constructor(
    private val authRepository: AuthRepository
): GetRecentAirportUseCase {
    override suspend operator fun invoke(): MutableSet<String>? {
        return authRepository.getRecentAirport()
    }
}