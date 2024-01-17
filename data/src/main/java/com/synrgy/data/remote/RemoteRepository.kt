package com.synrgy.data.remote

import com.synrgy.data.remote.service.RemoteService
import com.synrgy.domain.Login
import com.synrgy.domain.LoginBody
import com.synrgy.domain.Register
import com.synrgy.domain.RegisterBody
import com.synrgy.domain.repository.GuestRepository
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val remoteService: RemoteService
): GuestRepository {
    override suspend fun login(user: LoginBody): Login {
        return if (user.email == "zachrie2005@gmail.com" && user.password == "password") {
            Login(
                message = "Login success!",
                token = "token"
            )
        } else {
            throw UnsupportedOperationException("Login failed")
        }
    }

    override suspend fun register(user: RegisterBody): Register {
        return Register(
            message = "Register success!"
        )
    }
}