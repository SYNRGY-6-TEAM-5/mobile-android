package com.synrgy.domain.repository

import com.synrgy.domain.Resource
import com.synrgy.domain.response.user.UserDetailResponse

interface UserRepository {
    suspend fun getUserDetail(
        token: String
    ): Resource<UserDetailResponse>
}