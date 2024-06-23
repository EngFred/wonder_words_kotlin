package com.kotlin.wonderwords.features.auth.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.wonderwords.core.network.DataState
import com.kotlin.wonderwords.features.auth.domain.entity.AuthRequest
import com.kotlin.wonderwords.features.auth.domain.entity.User
import com.kotlin.wonderwords.features.auth.domain.usecase.SignUpUseCase
import com.kotlin.wonderwords.features.auth.domain.usecase.ValidateEmailUseCase
import com.kotlin.wonderwords.features.auth.domain.usecase.ValidatePasswordUseCase
import com.kotlin.wonderwords.features.auth.domain.usecase.ValidateUsernameUseCase
import com.kotlin.wonderwords.features.auth.presentation.screens.signup.SignupUiEvents
import com.kotlin.wonderwords.features.auth.presentation.screens.signup.SignupUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateUsernameUseCase: ValidateUsernameUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent( event: SignupUiEvents) {
        if (_uiState.value.signupError != null) {
            _uiState.update {
                it.copy(signupError = null)
            }
        }
        when(event) {
            is SignupUiEvents.NameChanged-> {
                val validation = validateUsernameUseCase(event.name)
                _uiState.update {
                    it.copy(
                        name = event.name,
                        usernameError = if (validation.isValid) null else validation.errorMessage
                    )
                }
            }
            is SignupUiEvents.EmailChanged -> {
                val validation = validateEmailUseCase(event.email)
                _uiState.update {
                    it.copy(
                        email = event.email,
                        emailError = if (validation.isValid) null else validation.errorMessage
                    )
                }
            }
            is SignupUiEvents.PasswordChanged -> {
                val validation = validatePasswordUseCase(event.password)
                _uiState.update {
                    it.copy(
                        password = event.password,
                        passwordError = if (validation.isValid) null else validation.errorMessage
                    )
                }
            }
            is SignupUiEvents.ConfirmedPasswordChanged-> {
                _uiState.update {
                    it.copy(
                        confirmedPassword = event.confirmedPassword
                    )
                }
            }
            is SignupUiEvents.SignupButtonClicked -> {
                validateForm()
                if (_uiState.value.isFormValid) {
                    _uiState.update {
                        it.copy(isLoading = true)
                    }
                    signUpUser( _uiState.value.name, _uiState.value.email, _uiState.value.password)
                }
            }
        }
        validateForm()
    }


    private fun validateForm() {
        val isEmailValid = validateEmailUseCase(_uiState.value.email).isValid
        val isPasswordValid = validatePasswordUseCase(_uiState.value.password).isValid
        val isUsernameValid = validateUsernameUseCase(_uiState.value.name).isValid
        val isConfirmedPasswordValid = _uiState.value.password == _uiState.value.confirmedPassword

        _uiState.update {
            it.copy(
                isFormValid = isEmailValid && isPasswordValid && isUsernameValid && isConfirmedPasswordValid
            )
        }
    }

    private fun signUpUser(
        name: String,
        email: String,
        password: String
    ) = viewModelScope.launch( Dispatchers.IO ) {

        val user = User(
            login = name.trim(),
            email = email.trim(),
            password = password.trim()
        )

        val signUpRequest = AuthRequest(
            user = user
        )

        val request = signUpUseCase(signUpRequest)

        when(request){
            is DataState.Error -> {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        signupError = request.error
                    )
                }
            }
            DataState.Loading -> Unit
            is DataState.Success -> {
                _uiState.update {
                    it.copy(
                        signupSuccess = true
                    )
                }
            }
        }
    }

}