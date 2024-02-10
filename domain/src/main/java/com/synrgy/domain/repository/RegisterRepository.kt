package com.synrgy.domain.repository

interface RegisterRepository {
    suspend fun validateRegister(email: String, password: String): Boolean
}