package com.kotlin.wonderwords.features.quotes.presentation.screen

import com.kotlin.wonderwords.features.quotes.domain.entity.QuoteCategory

sealed class QuotesUiEvents {
    data class SelectedCategory(val category: QuoteCategory) : QuotesUiEvents()
    data class SearchQueryChanged(val query: String) : QuotesUiEvents()
}