package com.synrgy.domain.usecase.user

import com.synrgy.domain.Resource
import com.synrgy.domain.body.user.EditProfileBody
import com.synrgy.domain.response.user.EditProfileResponse

interface EditProfileUseCase {
    suspend fun invoke(
        token: String,
        body: EditProfileBody
    ): Resource<EditProfileResponse>
}