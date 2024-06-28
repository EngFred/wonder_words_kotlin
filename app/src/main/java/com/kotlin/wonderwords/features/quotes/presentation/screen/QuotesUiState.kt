package com.kotlin.wonderwords.features.quotes.presentation.screen


import com.kotlin.wonderwords.features.quotes.domain.models.Quote
import com.kotlin.wonderwords.features.quotes.domain.models.QuoteCategory
import com.kotlin.wonderwords.features.quotes.domain.models.Source

data class QuotesUiState(
    val selectedCategory: QuoteCategory = QuoteCategory.All,
    val searchQuery: String = "",
    val refreshing: Boolean = true,
    val initialLoading: Boolean = true,
    val refreshError: String? = null,
    val quotes: List<Quote> = emptyList(),
    val source: Source = Source.Cache
)
