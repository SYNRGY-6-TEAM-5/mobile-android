package com.synrgy.domain.repository

import com.synrgy.domain.Resource
import com.synrgy.domain.body.user.EditProfileBody
import com.synrgy.domain.response.auth.UploadProfileImageResponse
import com.synrgy.domain.response.user.EditProfileImageResponse
import com.synrgy.domain.response.user.EditProfileResponse
import com.synrgy.domain.response.user.UserDetailResponse
import okhttp3.MultipartBody

interface UserRepository {
    suspend fun getUserDetail(
        token: String
    ): Resource<UserDetailResponse>

    suspend fun editProfile(
        token: String,
        body: EditProfileBody
    ): Resource<EditProfileResponse>

    suspend fun uploadProfileImage(
        token: String,
        name: String,
        file: MultipartBody.Part
    ): Resource<UploadProfileImageResponse>

    suspend fun editProfileImage(
        token: String,
        file: MultipartBody.Part
    ): Resource<EditProfileImageResponse>
}