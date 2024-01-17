package com.synrgy.domain.repository

import com.synrgy.domain.LoginBody
import com.synrgy.domain.Login
import com.synrgy.domain.Register
import com.synrgy.domain.RegisterBody

interface GuestRepository {
    suspend fun register(user: RegisterBody): Register
    suspend fun login(user: LoginBody): Login
}

