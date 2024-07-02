package com.kotlin.wonderwords.features.user_update.data.repository

import android.util.Log
import com.kotlin.wonderwords.core.network.DataState
import com.kotlin.wonderwords.features.auth.data.token_manager.TokenManager
import com.kotlin.wonderwords.features.user_update.data.api.UpdateUserApiService
import com.kotlin.wonderwords.features.user_update.data.models.UserUpdateResponse
import com.kotlin.wonderwords.features.user_update.domain.models.UserUpdateRequest
import com.kotlin.wonderwords.features.user_update.domain.repository.UpdateUserRepository
import okio.IOException
import java.net.ConnectException
import javax.inject.Inject

class UpdateUserInfoRepositoryImpl @Inject constructor(
    private val updateUserApiService: UpdateUserApiService,
    private val tokenManager: TokenManager
) : UpdateUserRepository {

    companion object {
        const val TAG = "UpdateUserInfoRepositoryImpl"
    }

    override suspend fun updateUserInfo(
        username: String,
        userUpdateRequest: UserUpdateRequest
    ): DataState<UserUpdateResponse> {
        Log.v(TAG, "$userUpdateRequest")
        return try {
            Log.d(TAG, "Updating user info...")

            val userToken = tokenManager.getToken() ?: return DataState.Error("No token found")
            val res = updateUserApiService.updateUserInfo( userToken, username, userUpdateRequest)

            if (res.errorCode == null) {
                if ( userUpdateRequest.user.login != null && userUpdateRequest.user.email != null ) {
                    tokenManager.saveUsername(userUpdateRequest.user.login)
                    tokenManager.saveUserEmail(userUpdateRequest.user.email)
                    Log.d(TAG, "Success updating user info ${res.message}")
                    DataState.Success(res)
                } else {
                    Log.d(TAG, "Success updating user info ${res.message}")
                    DataState.Success(res)
                }
            } else {
                Log.d(TAG, "An error occurred! ${res.message}")
                DataState.Error("${res.message}")
            }

        }catch (e: Exception) {
            if (e is ConnectException || e is IOException || e.cause is IOException) {
                Log.e(TAG, "No internet connection!")
                DataState.Error("No internet connection!")
            } else{
                Log.d(TAG, "Error updating user info in catch block ${e.message}")
                DataState.Error(e.message.toString())
            }
        }
    }
}