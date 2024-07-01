package com.kotlin.wonderwords.features.auth.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.wonderwords.core.network.DataState
import com.kotlin.wonderwords.features.auth.domain.models.AuthRequest
import com.kotlin.wonderwords.features.auth.domain.models.User
import com.kotlin.wonderwords.features.auth.domain.usecase.ForgotPasswordUseCase
import com.kotlin.wonderwords.features.auth.domain.usecase.ValidateEmailUseCase
import com.kotlin.wonderwords.features.auth.presentation.screens.forgot_password.ForgotPasswordUiEvents
import com.kotlin.wonderwords.features.auth.presentation.screens.forgot_password.ForgotPasswordUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val forgotPasswordUseCase: ForgotPasswordUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
): ViewModel() {

    private val _uiState = MutableStateFlow(ForgotPasswordUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent( event: ForgotPasswordUiEvents ) {
        when(event) {
            is ForgotPasswordUiEvents.EmailChanged -> {
                val validation = validateEmailUseCase(event.email)
                _uiState.update {
                    it.copy(
                        email = event.email,
                        emailError = if (validation.isValid) null else validation.errorMessage
                    )
                }
            }
            ForgotPasswordUiEvents.SubmitButtonClicked -> {
                validateForm()
                if (_uiState.value.isFormValid) {
                    _uiState.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                    forgotPassword(_uiState.value.email)
                }
            }
        }
    }

    private fun validateForm() {
        val isEmailValid = validateEmailUseCase(_uiState.value.email).isValid

        _uiState.update {
            it.copy(
                isFormValid = isEmailValid
            )
        }
    }


    private fun forgotPassword(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = User(
                email = email
            )
            val authRequest = AuthRequest(
                user = user
            )
            val request = forgotPasswordUseCase.invoke(authRequest)

            when(request) {
                is DataState.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = request.error
                        )
                    }
                }
                DataState.Loading -> Unit
                is DataState.Success -> {
                    _uiState.update {
                        it.copy(
                            sentResetLink = true //also disable the button
                        )
                    }
                }
            }
        }
    }
}