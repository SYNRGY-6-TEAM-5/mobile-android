package com.synrgy.presentation.usecase.auth

import com.synrgy.domain.repository.AuthRepository
import com.synrgy.domain.usecase.auth.GetPhotoUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPhotoUseCase @Inject constructor(
    private val authRepository: AuthRepository
): GetPhotoUseCase {
    override suspend operator fun invoke(): Flow<String?> {
        return authRepository.getPhoto()
    }
}