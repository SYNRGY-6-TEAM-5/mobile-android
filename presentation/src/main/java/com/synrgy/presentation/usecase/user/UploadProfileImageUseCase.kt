package com.synrgy.presentation.usecase.user

import com.synrgy.domain.Resource
import com.synrgy.domain.repository.UserRepository
import com.synrgy.domain.response.auth.UploadProfileImageResponse
import com.synrgy.domain.usecase.user.UploadProfileImageUseCase
import okhttp3.MultipartBody
import javax.inject.Inject

class UploadProfileImageUseCase @Inject constructor(
    private val userRepository: UserRepository
): UploadProfileImageUseCase {
    override suspend operator fun invoke(
        token: String,
        name: String,
        file: MultipartBody.Part
    ): Resource<UploadProfileImageResponse> {
        return userRepository.uploadProfileImage(token, name, file)
    }
}