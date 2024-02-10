package com.synrgy.domain.repository

import com.synrgy.domain.Resource
import com.synrgy.domain.response.departure.DepartureResponse

interface DepartureRepository {
    suspend fun getDeparture(): Resource<DepartureResponse>
}