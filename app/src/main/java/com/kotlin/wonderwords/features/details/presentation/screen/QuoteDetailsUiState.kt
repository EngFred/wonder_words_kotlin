package com.kotlin.wonderwords.features.details.presentation.screen

import com.kotlin.wonderwords.features.details.domain.models.QuoteDetails

data class QuoteDetailsUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val quote: QuoteDetails? = null,
)
