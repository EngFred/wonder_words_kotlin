package com.kotlin.wonderwords.features.quotes.presentation.screen

import com.kotlin.wonderwords.features.quotes.domain.entity.QuoteCategory

data class QuotesUiState(
    val selectedCategory: QuoteCategory = QuoteCategory.All,
    val searchQuery: String = ""
)
