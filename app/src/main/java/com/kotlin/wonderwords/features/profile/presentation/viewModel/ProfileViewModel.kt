package com.kotlin.wonderwords.features.profile.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.wonderwords.core.network.DataState
import com.kotlin.wonderwords.features.profile.domain.usecases.GetUserDetailsUseCase
import com.kotlin.wonderwords.features.profile.domain.usecases.SignOutUserUseCase
import com.kotlin.wonderwords.features.profile.presentation.screen.ProfileUiEvents
import com.kotlin.wonderwords.features.profile.presentation.screen.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
    private val signOutUserUseCase: SignOutUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getUserDetails()
    }

    fun onEvent(event: ProfileUiEvents) {
        reset()
        when(event) {
            ProfileUiEvents.LoggedOut -> {
                _uiState.update {
                    it.copy(
                        signingOut = true
                    )
                }
                signOut()
            }

            ProfileUiEvents.Refreshed -> {
                if (!_uiState.value.isLoading && !_uiState.value.isRefreshing) {
                    _uiState.update {
                        it.copy(
                            isRefreshing = true
                        )
                    }
                    getUserDetails()
                }
            }
        }
    }

    private fun getUserDetails() = viewModelScope.launch {
        getUserDetailsUseCase().collectLatest { dataState ->
            when(dataState) {
                is DataState.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isRefreshing = false,
                            error = dataState.error
                        )
                    }
                }
                DataState.Loading -> Unit
                is DataState.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isRefreshing = false,
                            user = dataState.data
                        )
                    }
                }
            }
        }
    }

    private fun signOut() = viewModelScope.launch(Dispatchers.IO) {
        val dataState = signOutUserUseCase()

        when(dataState) {
            is DataState.Error -> {
                _uiState.update {
                    it.copy(
                        signingOut = false,
                        signOutError = dataState.error
                    )
                }
            }
            DataState.Loading -> {
                _uiState.update {
                    it.copy(
                        signingOut = true
                    )
                }
            }
            is DataState.Success -> {
                _uiState.update {
                    it.copy(
                        signOutSuccess = true
                    )
                }
            }
        }
    }

    fun reset() {
        if (_uiState.value.signOutError != null || _uiState.value.error != null) {
            _uiState.update {
                it.copy(
                    signOutError = null,
                    error = null
                )
            }
        }
    }
}