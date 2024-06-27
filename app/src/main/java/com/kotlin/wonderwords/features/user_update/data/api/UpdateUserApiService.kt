package com.kotlin.wonderwords.features.user_update.data.api

import com.kotlin.wonderwords.features.user_update.data.models.UserUpdateResponse
import com.kotlin.wonderwords.features.user_update.domain.models.UserUpdateRequest
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

interface UpdateUserApiService {
    @PUT("/api/users/{login}")
    suspend fun updateUserInfo(
        @Path("login") username: String,
        @Body request: UserUpdateRequest
    ): UserUpdateResponse

}