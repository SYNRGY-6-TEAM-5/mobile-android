package com.synrgy.presentation.usecase.user

import com.synrgy.domain.Resource
import com.synrgy.domain.repository.GuestRepository
import com.synrgy.domain.response.auth.UploadProfileImageResponse
import com.synrgy.domain.usecase.user.UploadProfileImageUseCase
import okhttp3.MultipartBody
import javax.inject.Inject

class UploadProfileImageUseCase @Inject constructor(
    private val guestRepository: GuestRepository
): UploadProfileImageUseCase {
    override suspend operator fun invoke(
        token: String,
        name: String,
        file: MultipartBody.Part
    ): Resource<UploadProfileImageResponse> {
        return guestRepository.uploadProfileImage(token, name, file)
    }
}