package com.kotlin.wonderwords.features.profile.domain.repository

import com.kotlin.wonderwords.core.network.DataState
import com.kotlin.wonderwords.features.profile.domain.model.UserProfileDetails
import kotlinx.coroutines.flow.Flow

interface UserProfileRepository {
    suspend fun getUserDetails() : Flow<DataState<UserProfileDetails>>
    suspend fun signOutUser() : DataState<String>
}