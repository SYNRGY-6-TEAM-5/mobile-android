package com.synrgy.domain.repository

import com.synrgy.domain.response.airport.AirportData
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun clearToken()
    suspend fun getName(): Flow<String?>
    suspend fun setName(name: String)
    suspend fun getPhoto(): Flow<String?>
    suspend fun setPhoto(photoUrl: String)
    suspend fun getRegToken(): Flow<String?>
    suspend fun setRegToken(token: String)
    suspend fun setRecentAirport(data: AirportData)
    suspend fun getRecentAirport(): MutableSet<String>?
    suspend fun getUserId(): Flow<String?>
    suspend fun setUserId(id: String)
}