package com.kotlin.wonderwords.features.auth.data.source

import com.kotlin.wonderwords.features.auth.data.model.AuthResponse
import com.kotlin.wonderwords.features.auth.domain.entity.AuthRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("/api/users")
    suspend fun signUp( @Body authRequest: AuthRequest) : AuthResponse

    @POST("/api/session")
    suspend fun signIn( @Body signInRequest: AuthRequest) : AuthResponse

    @POST("/api/users/forgot_password")
    suspend fun forgotPassword( @Body request: AuthRequest) : AuthResponse

    @POST("/api/users/reset_password")
    suspend fun resetPassword( @Body request: AuthRequest) : AuthResponse

}