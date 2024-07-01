package com.kotlin.wonderwords.features.profile.data.repository

import android.util.Log
import com.kotlin.wonderwords.core.network.DataState
import com.kotlin.wonderwords.features.auth.data.token_manager.TokenManager
import com.kotlin.wonderwords.features.profile.data.api.UserProfileApiService
import com.kotlin.wonderwords.features.profile.data.mapper.toUserProfileDetails
import com.kotlin.wonderwords.features.profile.domain.model.UserProfileDetails
import com.kotlin.wonderwords.features.profile.domain.repository.UserProfileRepository
import com.kotlin.wonderwords.features.quotes.data.local.db.QuotesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserProfileRepositoryImpl @Inject constructor(
    private val userProfileApiService: UserProfileApiService,
    private val tokenManager: TokenManager,
    private val quotesDatabase: QuotesDatabase
) : UserProfileRepository {

    companion object {
        const val TAG = "ProfileRepositoryImpl"
    }
    override suspend fun getUserDetails(): Flow<DataState<UserProfileDetails>> {

        return flow {
            emit(DataState.Loading)
            val username = tokenManager.getUserName.firstOrNull()
            val userToken = tokenManager.getToken()
            if (username.isNullOrEmpty() || userToken.isNullOrEmpty()) {
                Log.e(TAG, "User info is null")
                emit(DataState.Error("User info is null"))
            } else {
                val userProfileDetailsDTO = userProfileApiService.getUserDetails(username, userToken)
                val userProfileDetails = userProfileDetailsDTO.toUserProfileDetails()
                emit(DataState.Success(userProfileDetails))
            }
        }.flowOn(Dispatchers.IO).catch {
            Log.e(TAG, "Error fetching user details: ${it.localizedMessage}")
            emit(DataState.Error("Error fetching user details"))
        }
    }

    override suspend fun signOutUser() : DataState<Boolean> {
        return try {
            val userToken = tokenManager.getToken() ?: return DataState.Error("Something went wrong!")
            val signOutResponse = userProfileApiService.signOutUser(userToken)
            if ( signOutResponse.errorCode == null && signOutResponse.message != null ) {
                quotesDatabase.quotesDao().clearAllQuotes()
                tokenManager.clearUserInfo()
                DataState.Success(true)
            } else {
                DataState.Error("Something went wrong!")
            }
        }catch (e: Exception) {
            Log.e(TAG, "Error signing out user: ${e.localizedMessage}")
            DataState.Error("Something went wrong!")
        }
    }
}