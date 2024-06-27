package com.kotlin.wonderwords.features.user_update.domain.repository

import com.kotlin.wonderwords.core.network.DataState
import com.kotlin.wonderwords.features.user_update.data.models.UserUpdateResponse
import com.kotlin.wonderwords.features.user_update.domain.models.UserUpdateRequest

interface UpdateUserRepository {
    suspend fun updateUserInfo(
        username: String,
        userUpdateRequest: UserUpdateRequest
    ) : DataState<UserUpdateResponse>
}