package com.synrgy.presentation.usecase.user

import com.synrgy.domain.Resource
import com.synrgy.domain.body.user.EditProfileBody
import com.synrgy.domain.repository.GuestRepository
import com.synrgy.domain.response.user.EditProfileResponse
import com.synrgy.domain.usecase.user.EditProfileUseCase
import javax.inject.Inject

class EditProfileUseCase @Inject constructor(
    private val guestRepository: GuestRepository
): EditProfileUseCase {
    override suspend operator fun invoke(
        token: String,
        body: EditProfileBody,
    ): Resource<EditProfileResponse> {
        return guestRepository.editProfile(token, body)
    }
}