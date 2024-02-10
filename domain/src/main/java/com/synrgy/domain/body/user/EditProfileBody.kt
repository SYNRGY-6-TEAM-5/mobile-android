package com.synrgy.domain.body.user


data class EditProfileBody(
    val dob: String,
    val fullName: String,
    val phoneNumber: Long
)