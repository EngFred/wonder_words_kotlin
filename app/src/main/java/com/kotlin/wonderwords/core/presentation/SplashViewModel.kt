package com.kotlin.wonderwords.core.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.wonderwords.features.auth.data.token_manager.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _userToken = MutableStateFlow<String?>(null)
    val userToken = _userToken

    companion object {
        const val TAG = "SplashViewModel"
    }

    init {
        getUserInfo()
    }

    private fun getUserInfo() = viewModelScope.launch(Dispatchers.IO) {
        Log.i(TAG, "STARTED!")
        val userToken = tokenManager.getUserToken.first()
        _userToken.value = userToken
        Log.i(TAG, "FINISHED!")
    }
}