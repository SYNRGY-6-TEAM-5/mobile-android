package com.synrgy.domain.usecase.user

import com.synrgy.domain.Resource
import com.synrgy.domain.response.user.EditProfileImageResponse
import okhttp3.MultipartBody

interface EditProfileImageUseCase {
    suspend fun invoke(
        token: String,
        file: MultipartBody.Part
    ): Resource<EditProfileImageResponse>
}