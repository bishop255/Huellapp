package com.example.huellapp.repository

import com.example.huellapp.api.AuthApiService
import com.example.huellapp.model.remote.LoginRequest

class AuthRepository(private val api: AuthApiService) {

    suspend fun login(email: String, password: String) =
        api.login(LoginRequest(email, password))
}
