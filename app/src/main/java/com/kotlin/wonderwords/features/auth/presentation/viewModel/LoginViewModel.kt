package com.kotlin.wonderwords.features.auth.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.wonderwords.core.network.DataState
import com.kotlin.wonderwords.features.auth.domain.models.AuthRequest
import com.kotlin.wonderwords.features.auth.domain.models.User
import com.kotlin.wonderwords.features.auth.domain.usecase.SignInUseCase
import com.kotlin.wonderwords.features.auth.presentation.screens.login.LoginUiEvents
import com.kotlin.wonderwords.features.auth.presentation.screens.login.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()
    
    fun onEvent( event: LoginUiEvents ) {
        if (_uiState.value.loginError != null) {
            _uiState.update {
                it.copy(loginError = null)
            }
        }
        when(event) {
            is LoginUiEvents.LoginChanged -> {
                _uiState.update { 
                    it.copy(
                        login = event.login
                    )
                }
            }
            is LoginUiEvents.PasswordChanged -> {
                _uiState.update { 
                    it.copy(
                        password = event.password
                    )
                }
            }
            is LoginUiEvents.LoginButtonClicked -> {
                validateForm()
                if (_uiState.value.isFormValid) {
                    _uiState.update { 
                        it.copy(isLoading = true)
                    }
                    signInUser(_uiState.value.login, _uiState.value.password)
                }
            }
        }
        validateForm()
    }

    private fun validateForm() {
        val isLoginValid = _uiState.value.login.isNotBlank()
        val isPasswordValid = _uiState.value.password.isNotBlank()

        _uiState.update {
            it.copy(
                isFormValid = isLoginValid && isPasswordValid
            )
        }
    }

    private fun signInUser(login: String, password: String) = viewModelScope.launch( Dispatchers.IO ) {
        
        val user = User(
            login = login,
            password = password
        )

        val signInRequest = AuthRequest(
            user = user
        )
        
        val request = signInUseCase(signInRequest)
        
        when(request){
            is DataState.Error -> {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        loginError = request.error
                    )
                }
            }
            DataState.Loading -> Unit
            is DataState.Success -> {
                _uiState.update { 
                    it.copy(
                        logInSuccess = true
                    )
                }
            }
        }
    }

}