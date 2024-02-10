package com.synrgy.domain.usecase.airport

import com.synrgy.domain.Resource
import com.synrgy.domain.body.user.EditProfileBody
import com.synrgy.domain.response.airport.AirportResponse
import com.synrgy.domain.response.departure.DepartureResponse
import com.synrgy.domain.response.user.EditProfileResponse

interface AirportListUseCase {
    suspend fun invoke(): Resource<AirportResponse>
}