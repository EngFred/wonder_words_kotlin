package com.kotlin.wonderwords.core.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.wonderwords.core.presentation.ThemeManager
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
    private val themeManager: ThemeManager
) : ViewModel() {

    private val _theme = MutableStateFlow(ThemeMode.Light)
    val currentTheme = _theme.asStateFlow()

    init {
        getTheme()
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