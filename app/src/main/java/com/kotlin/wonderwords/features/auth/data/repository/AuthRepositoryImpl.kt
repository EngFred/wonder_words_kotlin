package com.kotlin.wonderwords.features.auth.data.repository

import android.util.Log
import com.kotlin.wonderwords.core.network.DataState
import com.kotlin.wonderwords.features.auth.data.model.AuthResponse
import com.kotlin.wonderwords.features.auth.data.source.AuthApiService
import com.kotlin.wonderwords.features.auth.data.token_manager.TokenManager
import com.kotlin.wonderwords.features.auth.domain.models.AuthRequest
import com.kotlin.wonderwords.features.auth.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private  val authApiService: AuthApiService,
    private val tokenManager: TokenManager
) : AuthRepository {

    companion object {
        private const val TAG = "AuthRepositoryImpl"
    }

    override suspend fun signUp(signupRequest: AuthRequest): DataState<AuthResponse> {
        try {
            Log.i(TAG, "Creating user....")
            val apiResponse = authApiService.signUp(signupRequest)
            Log.i(TAG, "$apiResponse")
            if (apiResponse.userToken != null && apiResponse.userName != null) {
                tokenManager.saveUserToken(apiResponse.userToken)
                tokenManager.saveUsername(apiResponse.userName)
                tokenManager.saveUserEmail(signupRequest.user.email!!)
                Log.i(TAG, "User info saved successfully!\n" +
                        "Username: ${apiResponse.userName}\n" +
                        "UserEmail: ${apiResponse.email}\n" +
                        "UserToken: ${apiResponse.userToken}")
                return DataState.Success(apiResponse)
            }
            Log.i(TAG, "Signup failed! userToken or userName returned is null!")
            return DataState.Error(apiResponse.message ?: "Unknown error") //tho here it wont be null
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
            return DataState.Error(e.message.toString())
        }
    }

    override suspend fun signIn(signInRequest: AuthRequest): DataState<AuthResponse> {
        try {
            Log.i(TAG, "Logging in with request body $signInRequest....")
            val apiResponse = authApiService.signIn(signInRequest)
            Log.i(TAG, "$apiResponse")
            if (apiResponse.userToken != null && apiResponse.userName != null && apiResponse.email != null) {
                tokenManager.saveUserToken(apiResponse.userToken)
                tokenManager.saveUsername(apiResponse.userName)
                tokenManager.saveUserEmail(apiResponse.email)
                Log.v(TAG, "User info saved successfully!\nUsername: ${apiResponse.userName}\nUserEmail: ${apiResponse.email}\nUserToken: ${apiResponse.userToken}")
                return DataState.Success(apiResponse)
            }
            Log.i(TAG, "Login failed! userToken or userName returned is null!")
            return DataState.Error(apiResponse.message ?: "Unknown error") //tho here it wont be null
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
            return DataState.Error(e.message.toString())
        }
    }

    override suspend fun forgotPassword(request: AuthRequest): DataState<AuthResponse> {
        try {
            val apiResponse = authApiService.forgotPassword(request)
            Log.i(TAG, "$apiResponse")
            if (apiResponse.errorCode != null) {
                return DataState.Success(apiResponse)
            }
            return DataState.Error(apiResponse.message ?: "Unknown error")
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
            return DataState.Error(e.message.toString())
        }
    }

    override suspend fun resetPassword(request: AuthRequest): DataState<AuthResponse> {
        try {
            val apiResponse = authApiService.resetPassword(request)
            Log.i(TAG, "$apiResponse")
            if (apiResponse.errorCode != null) {
                return DataState.Success(apiResponse)
            }
            //when successfully updated password, user is logged out and redirected to login screen to login again
            return DataState.Error(apiResponse.message ?: "Unknown error")
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
            return DataState.Error(e.message.toString())
        }
    }
}