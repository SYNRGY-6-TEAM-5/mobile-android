package com.synrgy.presentation.usecase.departure

import com.synrgy.domain.Resource
import com.synrgy.domain.repository.DepartureRepository
import com.synrgy.domain.response.departure.DepartureResponse
import com.synrgy.domain.usecase.departure.GetDepartureUseCase
import javax.inject.Inject

class GetDepartureUseCase @Inject constructor(
    private val departureRepository: DepartureRepository
): GetDepartureUseCase {
    override suspend operator fun invoke(): Resource<DepartureResponse> {
        return departureRepository.getDeparture()
    }
}