package com.kotlin.wonderwords.features.quotes.presentation.screen

import com.kotlin.wonderwords.features.quotes.data.repository.Source
import com.kotlin.wonderwords.features.quotes.domain.models.Quote
import com.kotlin.wonderwords.features.quotes.domain.models.QuoteCategory

data class QuotesUiState(
    val selectedCategory: QuoteCategory = QuoteCategory.All,
    val searchQuery: String = "",
    val refreshing: Boolean = true,
    val initialLoading: Boolean = true,
    val refreshError: String? = null,
    val quotes: List<Quote> = emptyList(),
    val dataSource: Source = Source.Cache
)
