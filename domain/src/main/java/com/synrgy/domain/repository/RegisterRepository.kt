package com.synrgy.domain.repository

interface RegisterRepository {
    suspend fun validateInput(email: String, password: String): Boolean
}