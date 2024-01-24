package com.synrgy.domain.response.user


import com.google.gson.annotations.SerializedName

data class UserDetailResponse(
    @SerializedName("createdAt")
    val createdAt: Long?,
    @SerializedName("dob")
    val dob: Any?,
    @SerializedName("fullName")
    val fullName: Any?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("imageUrl")
    val imageUrl: Any?,
    @SerializedName("roleName")
    val roleName: String?,
    @SerializedName("success")
    val success: Boolean?
)