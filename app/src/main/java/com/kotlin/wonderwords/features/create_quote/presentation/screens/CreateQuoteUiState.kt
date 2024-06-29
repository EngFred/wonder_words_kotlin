package com.kotlin.wonderwords.features.create_quote.presentation.screens

data class CreateQuoteUiState(
    val isLoading: Boolean = false,
    val createError: String? = null,
    val author: String = "",
    val body: String = "",
    val bodyError: String? = null,
    val authorError: String? = null,
    val isValid: Boolean = false,
    val createdQuoteId: Int? = null
)
