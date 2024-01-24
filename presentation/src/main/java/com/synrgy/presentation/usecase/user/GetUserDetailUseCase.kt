package com.synrgy.presentation.usecase.user

import com.synrgy.domain.Resource
import com.synrgy.domain.repository.UserRepository
import com.synrgy.domain.response.user.UserDetailResponse
import com.synrgy.domain.usecase.user.GetUserDetailUseCase
import javax.inject.Inject

class GetUserDetailUseCase @Inject constructor(
    private val userRepository: UserRepository
): GetUserDetailUseCase {
    override suspend operator fun invoke(token: String): Resource<UserDetailResponse> {
        return userRepository.getUserDetail(token)
    }
}