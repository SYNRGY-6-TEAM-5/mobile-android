package com.synrgy.domain.usecase.user

import com.synrgy.domain.Resource
import com.synrgy.domain.response.user.UserDetailResponse

interface GetUserDetailUseCase {
    suspend fun invoke(
        token: String
    ): Resource<UserDetailResponse>
}