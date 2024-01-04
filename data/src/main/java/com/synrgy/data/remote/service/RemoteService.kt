package com.synrgy.data.remote.service

import com.synrgy.domain.LoginBody
import com.zachriek.data.remote.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface RemoteService {
    @POST("auth/login")
    suspend fun login(@Body user: LoginBody): LoginResponse
}