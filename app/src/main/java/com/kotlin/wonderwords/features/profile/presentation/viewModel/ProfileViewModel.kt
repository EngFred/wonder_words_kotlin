package com.kotlin.wonderwords.features.profile.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.kotlin.wonderwords.features.profile.presentation.screen.ProfileUiEvents
import com.kotlin.wonderwords.features.profile.presentation.screen.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: ProfileUiEvents) {
        when(event) {
            is ProfileUiEvents.ThemeChange -> {
                if ( _uiState.value.selectTheme != event.theme ) {
                    _uiState.update {
                        it.copy(
                            selectTheme = event.theme
                        )
                    }
                }
            }
        }
    }
}