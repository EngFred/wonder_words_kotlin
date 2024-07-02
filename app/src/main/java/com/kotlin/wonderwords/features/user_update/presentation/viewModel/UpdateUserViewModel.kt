package com.kotlin.wonderwords.features.user_update.presentation.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.wonderwords.core.network.DataState
import com.kotlin.wonderwords.features.auth.domain.usecase.ValidateEmailUseCase
import com.kotlin.wonderwords.features.auth.domain.usecase.ValidatePasswordUseCase
import com.kotlin.wonderwords.features.auth.domain.usecase.ValidateUsernameUseCase
import com.kotlin.wonderwords.features.user_update.domain.models.UserInfoToUpdate
import com.kotlin.wonderwords.features.user_update.domain.models.UserUpdateRequest
import com.kotlin.wonderwords.features.user_update.domain.usecases.UpdateUserInfoUseCase
import com.kotlin.wonderwords.features.user_update.presentation.screens.UpdateUserUiEvents
import com.kotlin.wonderwords.features.user_update.presentation.screens.UpdateUserUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateUserViewModel @Inject constructor(
    private val updateUserInfoUseCase: UpdateUserInfoUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateUsernameUseCase: ValidateUsernameUseCase
) : ViewModel() {


    private val oldUsername = savedStateHandle.get<String>("username")
    private val oldEmail = savedStateHandle.get<String>("email")


    private val _uiState = MutableStateFlow(UpdateUserUiState())
    val uiState = _uiState.asStateFlow()

    init {
        initializeState(
            oldUsername ?: "",
            oldEmail ?: ""
        )
    }

    private fun initializeState(oldUsername: String, oldEmail: String) {
        _uiState.update {
            it.copy(
                username = oldUsername,
                email = oldEmail
            )
        }
    }

    fun onEvent( event: UpdateUserUiEvents) {

        if ( _uiState.value.updateError != null ) {
            _uiState.update {
                it.copy(
                    updateError = null
                )
            }
        }

        when(event) {
            is UpdateUserUiEvents.EmailChanged -> {
                val validation = validateEmailUseCase(event.email)
                _uiState.update {
                    it.copy(
                        email = event.email,
                        emailError = if (validation.isValid) null else validation.errorMessage
                    )
                }
            }
            is UpdateUserUiEvents.FacebookUsernameChanged -> {
                _uiState.update {
                    it.copy(
                        facebookUsername = event.facebookUsername
                    )
                }
            }
            is UpdateUserUiEvents.UsernameChanged -> {
                val validation = validateUsernameUseCase(event.name)
                _uiState.update {
                    it.copy(
                        username = event.name,
                        usernameError = if (validation.isValid) null else validation.errorMessage
                    )
                }
            }
            is UpdateUserUiEvents.PasswordChanged -> {
                val validation = validatePasswordUseCase(event.password)
                _uiState.update {
                    it.copy(
                        password = event.password,
                        passwordError = if (validation.isValid) null else validation.errorMessage
                    )
                }
            }
            is UpdateUserUiEvents.ProfilePicSourceChanged -> {
                _uiState.update {
                    it.copy(
                        profilePicSource = event.source,
                        expanded = false
                    )
                }
            }
            is UpdateUserUiEvents.TwitterUsernameChanged -> {
                _uiState.update {
                    it.copy(
                        twitterUsername = event.twitterUsername
                    )
                }
            }

            UpdateUserUiEvents.UpdateButtonClicked -> {
                if ( oldUsername != null ) {
                    _uiState.update {
                        it.copy(
                            isUpdating = true
                        )
                    }
                    updateUserInfo(oldUsername)
                }
            }

            UpdateUserUiEvents.SelectSourceButtonClicked -> {
                _uiState.update {
                    it.copy(
                        expanded = !it.expanded
                    )
                }
            }
        }
    }

    private fun updateUserInfo(oldUsername: String) = viewModelScope.launch(Dispatchers.IO) {

        val userInfoToUpdate = UserInfoToUpdate(
            login = _uiState.value.username?.lowercase()?.trim(),
            email = _uiState.value.email?.trim(),
            password = _uiState.value.password?.trim(),
            facebook_username = _uiState.value.facebookUsername,
            twitter_username = _uiState.value.twitterUsername,
            pic = _uiState.value.profilePicSource?.name?.lowercase()
        )
        val userUpdateRequest = UserUpdateRequest(
            user = userInfoToUpdate
        )

        val dataState = updateUserInfoUseCase(oldUsername, userUpdateRequest)

        when(dataState) {
            is DataState.Error -> {
                _uiState.update {
                    it.copy(
                        isUpdating = false,
                        updateError = dataState.error
                    )
                }
            }
            DataState.Loading -> {
                _uiState.update {
                    it.copy(
                        isUpdating = true
                    )
                }
            }
            is DataState.Success -> {
                _uiState.update {
                    it.copy(
                        updateSuccess = true
                    )
                }
            }
        }
    }
}