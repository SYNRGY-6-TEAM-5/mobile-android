package com.synrgy.domain.repository

import com.synrgy.domain.Login
import com.synrgy.domain.LoginBody
import com.synrgy.domain.User

interface GuestRepository {
    suspend fun register(user: User): String
    suspend fun login(user: LoginBody): Login
}

