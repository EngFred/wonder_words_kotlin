package com.kotlin.wonderwords.features.profile.data.api

import com.kotlin.wonderwords.features.profile.data.model.SignOutResponse
import com.kotlin.wonderwords.features.profile.data.model.UserProfileDetailsDTO
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface UserProfileApiService {
    @GET("users/{login}")
    suspend fun getUserDetails(@Path("login") username: String): UserProfileDetailsDTO

    @DELETE("/api/session")
    suspend fun signOutUser() : SignOutResponse

}