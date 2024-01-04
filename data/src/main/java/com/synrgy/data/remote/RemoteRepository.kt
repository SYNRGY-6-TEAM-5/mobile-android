package com.synrgy.data.remote

import com.synrgy.data.remote.service.RemoteService
import com.synrgy.domain.Login
import com.synrgy.domain.LoginBody
import com.synrgy.domain.User
import com.synrgy.domain.repository.GuestRepository
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val remoteService: RemoteService
): GuestRepository {
    override suspend fun login(user: LoginBody): Login {
        return Login(
            message = remoteService.login(user).message,
            token = remoteService.login(user).data.token
        )
    }

    override suspend fun register(user: User): String {
        TODO("Not yet implemented")
    }
}