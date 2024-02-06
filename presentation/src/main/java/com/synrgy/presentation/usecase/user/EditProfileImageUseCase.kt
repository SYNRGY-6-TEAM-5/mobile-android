package com.synrgy.presentation.usecase.user

import com.synrgy.domain.Resource
import com.synrgy.domain.repository.UserRepository
import com.synrgy.domain.response.user.EditProfileImageResponse
import com.synrgy.domain.usecase.user.EditProfileImageUseCase
import okhttp3.MultipartBody
import javax.inject.Inject

class EditProfileImageUseCase @Inject constructor(
    private val userRepository: UserRepository
): EditProfileImageUseCase {
    override suspend operator fun invoke(
        token: String,
        file: MultipartBody.Part
    ): Resource<EditProfileImageResponse> {
        return userRepository.editProfileImage(token, file)
    }
}