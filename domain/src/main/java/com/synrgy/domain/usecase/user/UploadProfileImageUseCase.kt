package com.synrgy.domain.usecase.user

import com.synrgy.domain.Resource
import com.synrgy.domain.response.auth.UploadProfileImageResponse
import okhttp3.MultipartBody

interface UploadProfileImageUseCase {
    suspend fun invoke(
        token: String,
        name: String,
        file: MultipartBody.Part
    ): Resource<UploadProfileImageResponse>
}