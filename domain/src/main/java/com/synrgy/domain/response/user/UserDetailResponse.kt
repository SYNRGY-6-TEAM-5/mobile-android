package com.synrgy.domain.response.user


import com.google.gson.annotations.SerializedName

data class UserDetailResponse(
    @SerializedName("createdAt")
    val createdAt: Long?,
    @SerializedName("dob")
    val dob: Long?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("fullName")
    val fullName: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("imageUrl")
    val imageUrl: String?,
    @SerializedName("phoneNum")
    val phoneNum: Long?,
    @SerializedName("roleName")
    val roleName: String?,
    @SerializedName("success")
    val success: Boolean?
)