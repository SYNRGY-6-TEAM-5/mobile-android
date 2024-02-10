package com.synrgy.domain.usecase.departure

import com.synrgy.domain.Resource
import com.synrgy.domain.body.user.EditProfileBody
import com.synrgy.domain.response.departure.DepartureResponse
import com.synrgy.domain.response.user.EditProfileResponse

interface GetDepartureUseCase {
    suspend fun invoke(): Resource<DepartureResponse>
}