package com.kotlin.wonderwords.core.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.wonderwords.core.presentation.ThemeManager
import com.kotlin.wonderwords.features.auth.data.token_manager.TokenManager
import com.kotlin.wonderwords.features.profile.domain.model.ThemeMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val themeManager: ThemeManager,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _theme = MutableStateFlow(ThemeMode.System)
    val currentTheme = _theme.asStateFlow()

    private val _username = MutableStateFlow("-")
    val username = _username.asStateFlow()

    private val _userEmail = MutableStateFlow("-")
    val userEmail = _userEmail.asStateFlow()

    private val _userToken = MutableStateFlow<String?>(null)
    val userToken = _userToken.asStateFlow()

    init {
        getTheme()
        getUserToken()
        getUsername()
        getUserEmail()
    }

    private fun getUserToken() = viewModelScope.launch {
        tokenManager.getUserToken.collectLatest {
            _userToken.value = it
        }
    }

    private fun getUsername() = viewModelScope.launch {
        tokenManager.getUserName.collectLatest {
            _username.value = it
        }
    }

    private fun getUserEmail() = viewModelScope.launch {
        tokenManager.getUserEmail.collectLatest {
            _userEmail.value = it
        }
    }

    private fun getTheme() = viewModelScope.launch {
        themeManager.getTheme.collectLatest { theme ->
            when (theme) {
                ThemeMode.Light.name.lowercase() -> {
                    _theme.value = ThemeMode.Light
                }
                ThemeMode.Dark.name.lowercase() -> {
                    _theme.value = ThemeMode.Dark
                }
                ThemeMode.System.name.lowercase() -> {
                    _theme.value = ThemeMode.System
                }
            }
        }
    }

    fun saveTheme( theme: ThemeMode) = viewModelScope.launch (Dispatchers.IO){
        _theme.value = theme
        themeManager.saveTheme(theme)
    }
}