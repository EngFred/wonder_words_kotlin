package com.kotlin.wonderwords.features.auth.domain.repository

import com.kotlin.wonderwords.core.network.DataState
import com.kotlin.wonderwords.features.auth.data.model.AuthResponse
import com.kotlin.wonderwords.features.auth.domain.entity.AuthRequest

interface AuthRepository {
    suspend fun signUp( signupRequest: AuthRequest) : DataState<AuthResponse>
    suspend fun signIn( signInRequest: AuthRequest) : DataState<AuthResponse>
    suspend fun forgotPassword(request: AuthRequest) : DataState<AuthResponse>
    suspend fun resetPassword(request: AuthRequest) : DataState<AuthResponse>
}