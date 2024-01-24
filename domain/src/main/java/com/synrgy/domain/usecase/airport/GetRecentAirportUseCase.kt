package com.synrgy.domain.usecase.airport

interface GetRecentAirportUseCase {
    suspend fun invoke(): MutableSet<String>?
}