package com.synrgy.presentation.usecase.forgotpassword

import com.synrgy.domain.Resource
import com.synrgy.domain.body.forgotpassword.EditPasswordFpBody
import com.synrgy.domain.repository.GuestRepository
import com.synrgy.domain.response.forgotpassword.EditPasswordFpResponse
import com.synrgy.domain.usecase.forgotpassword.EditPasswordFpUseCase
import javax.inject.Inject

class EditPasswordFpUseCase @Inject constructor(
    private val guestRepository: GuestRepository
): EditPasswordFpUseCase {
    override suspend operator fun invoke(
        token: String,
        body: EditPasswordFpBody
    ): Resource<EditPasswordFpResponse> {
        return guestRepository.editPasswordFp(token, body)
    }
}