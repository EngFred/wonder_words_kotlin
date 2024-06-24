package com.kotlin.wonderwords.features.quotes.presentation.screen

import com.kotlin.wonderwords.features.quotes.domain.domain.QuoteCategory

data class QuotesUiState(
    val selectedCategory: QuoteCategory = QuoteCategory.All,
    val searchQuery: String = ""
)
