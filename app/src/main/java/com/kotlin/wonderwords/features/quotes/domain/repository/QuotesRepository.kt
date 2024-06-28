package com.kotlin.wonderwords.features.quotes.domain.repository

import com.kotlin.wonderwords.core.network.DataState
import com.kotlin.wonderwords.features.quotes.data.repository.DataSource
import com.kotlin.wonderwords.features.quotes.domain.models.QuoteCategory

interface QuotesRepository {
    suspend fun fetchQuotes( page: Int, category: QuoteCategory) : DataState<DataSource>
}